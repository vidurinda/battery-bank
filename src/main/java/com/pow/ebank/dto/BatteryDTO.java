package com.pow.ebank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatteryDTO {
    private Long id;
    private String name;
    private Long postCode;
    private Integer wattCapacity;
}
