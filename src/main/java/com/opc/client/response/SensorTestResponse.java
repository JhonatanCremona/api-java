package com.opc.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
@Builder
@Getter
@Setter
public class SensorTestResponse  {
    private Long time;
    private String value;
}