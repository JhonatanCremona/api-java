package com.opc.client.service;

import com.opc.client.exception.ResourceNotFoundException;
import com.opc.client.models.SensorDigital;
import com.opc.client.models.SensorAnalogico;
import com.opc.client.repository.SensorRepository;
import com.opc.client.response.SensorResponse;
import com.opc.client.response.SensorTestResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SensorService {
    private static final Logger logger = Logger.getLogger(SensorService.class);
    private static int counter = 0;
    @Autowired
    OpcUaService opcUaService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    SensorRepository sensorRepository;
    public boolean existsComponentById(Integer id) {
        Optional<SensorDigital> temperatura = sensorRepository.findById(id);
        return temperatura.isPresent();
    }
    private void checkIfProductoExistsOrThrow(Integer id) {
        if (!existsComponentById(id)) {
            throw new ResourceNotFoundException(
                    "El sensor de temperatura con el id [%s] NO EXISTE".formatted(id)
            );
        }
    }
    public static Double roundToNDecimals(Double value, int decimals) {
        if (decimals < 0) {
            throw new IllegalArgumentException("El número de decimales no puede ser negativo.");
        }

        // Redondear usando BigDecimal
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimals, RoundingMode.HALF_UP);

        // Convertir de nuevo a Double
        return bd.doubleValue();
    }
    // Envia el dato cada 0.5 segundos
    public Collection<SensorDigital> listaSensores () throws Exception {
        Set<SensorDigital> listaSensores = new HashSet<SensorDigital>();
        for(int i=0; i<5; i++) {
            listaSensores.add(SensorDigital.builder()
                    .data(roundToNDecimals(opcUaService.readOpcUaVariableTest(),2))
                    .fechaInicio(LocalDateTime.now())
                    .modelo("Sensor")
                    .build()
            );
        }
        return listaSensores;
    }

    public Collection<SensorAnalogico> listaSensoresAnalogicos () throws Exception {
        Set<SensorAnalogico> listaSensores = new HashSet<SensorAnalogico>();
        for(int i=0; i<5; i++) {
            listaSensores.add(SensorAnalogico.builder()
                    .data(opcUaService.readOpcUaVariableTestBoolean())
                    .fechaInicio(LocalDateTime.now())
                    .modelo("Sensor")
                    .build()
            );
        }
        return listaSensores;
    }
    //@Cacheable("getSensorValues")
    public SensorResponse getSensorValue() throws Exception {
        Boolean sensorValue = null;
        try {
            sensorValue = opcUaService.readOpcUaVariableTestBoolean();
            return SensorResponse.builder()
                    .value(sensorValue)
                    .modelo("Sensor")
                    .timestamp(LocalDateTime.now())
                    .build();
        }catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(
                    "Fallo la lectura del DATO ANALOGICO: [%s]".formatted(sensorValue)
            );
        }
    }

    @Scheduled(fixedRate = 2000)
    public void saveSensorValueData() throws Exception {
        String valueToSave = "";
        try {
            valueToSave = String.format("%s;%s",
                    opcUaService.readOpcUaVariableTest().toString(), Instant.now().toEpochMilli());
            ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
            valueOp.set(getKey(),valueToSave, Duration.ofDays(1));
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    private String getKey() { counter++; return String.format("sensor:%s", counter);}
    public List<SensorTestResponse> listaSensorBoolean() {
        Set<String> keys = redisTemplate.keys("sensor:*");
        List<SensorTestResponse> responses = new ArrayList<>();

        for (String key : keys) {
            String sensorValue = redisTemplate.opsForValue().get(key);
            // Agregar cada SensorResponse a la lista
            System.out.println("DatoSensor: " + sensorValue);

            if (sensorValue != null) {
                String[] parts = sensorValue.split(";");
                if (parts.length == 2) {
                    Long formattedTimestamp = Instant.now().toEpochMilli();
                    System.out.println(parts);
                    System.out.println(Long.parseLong(parts[1]));
                    responses.add(SensorTestResponse.builder()
                            .value((parts[0]))
                            .time(Long.parseLong(parts[1]))
                            .build());
                }
            }
        }
        responses.sort(Comparator.comparingLong(SensorTestResponse::getTime));
        return responses;
    }


}
