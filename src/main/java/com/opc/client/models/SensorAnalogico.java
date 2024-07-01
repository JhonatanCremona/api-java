package com.opc.client.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorAnalogico {
    @Id
    @SequenceGenerator(name="sensorAnalogico_sequence", sequenceName = "sensorAnalogico_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensorAnalogico_sequence")
    private Integer idSensorAnalogico;
    private String modelo;
    private LocalDateTime fechaInicio;
    private Boolean data;
}
