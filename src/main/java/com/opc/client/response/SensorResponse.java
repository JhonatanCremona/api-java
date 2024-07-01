package com.opc.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter

public class SensorResponse implements Serializable {
    private Boolean value;
    private LocalDateTime timestamp;
    private String modelo;
}
