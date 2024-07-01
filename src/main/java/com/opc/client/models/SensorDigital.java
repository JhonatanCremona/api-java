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

public class SensorDigital {
    @Id
    @SequenceGenerator(name="dispositivo_sequence", sequenceName = "dispositivo_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dispositivo_sequence")
    private Integer idDispositivo;
    private String modelo;
    private LocalDateTime fechaInicio;
    private Double data;

}
