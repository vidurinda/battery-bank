package com.pow.ebank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatteryResponseDTO {
    private List<String> names;
    private BigDecimal totalWattCapacity;
    private BigDecimal averageWattCapacity;
}
