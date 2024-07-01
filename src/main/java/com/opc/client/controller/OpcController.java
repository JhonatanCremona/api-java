package com.opc.client.controller;

import com.opc.client.models.SensorAnalogico;
import com.opc.client.models.SensorDigital;
import com.opc.client.response.SensorResponse;
import com.opc.client.response.SensorTestResponse;
import com.opc.client.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/opcua")
@CrossOrigin
public class OpcController {

    @Autowired
    SensorService sensorService;
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/readVariable")
    public void readOpcUaVariable() throws Exception {
        readOpcUaVariable();
    }
    @GetMapping("/listaValores")
    public ResponseEntity<Collection<SensorDigital>> listaDataSensor() throws Exception {
        return ResponseEntity.ok(sensorService.listaSensores());
    }

    @GetMapping("/readVariableBoolean/{id}")
        public ResponseEntity<SensorResponse> readVariable(@PathVariable("id") Integer id) throws Exception {
            ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
            valueOp.set(getKey(id.toString()),sensorService.getSensorValue().toString(), Duration.ofSeconds(10));
            return ResponseEntity.ok(sensorService.getSensorValue());
        }
        private String getKey(String ID) {
            return String.format("sensor:%s", ID);
        }
    @GetMapping("/listaValoresAnalogicos")
    public ResponseEntity<Collection<SensorAnalogico>> listaDataSensorAnalogico() throws Exception {
        return ResponseEntity.ok(sensorService.listaSensoresAnalogicos());
    }

    @GetMapping("/get-stored-sensor-value")
    public ResponseEntity<List<SensorTestResponse>> getStoredSensorValue() {
        return ResponseEntity.ok(sensorService.listaSensorBoolean());
    }

}