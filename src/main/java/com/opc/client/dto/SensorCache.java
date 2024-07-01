package com.opc.client.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SensorCache implements Serializable {
    private Boolean value;
    private LocalDateTime timestamp;
    private String modelo;
}
