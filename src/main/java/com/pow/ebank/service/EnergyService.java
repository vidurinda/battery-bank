package com.pow.ebank.service;

import com.pow.ebank.dto.BatteryDTO;
import com.pow.ebank.dto.BatteryResponseDTO;

import java.util.List;
import java.util.Optional;

public interface EnergyService {

    List<BatteryDTO> save(List<BatteryDTO> batteries);

    List<BatteryDTO> getBatteryList(List<Long> postCodes);

    Optional<BatteryResponseDTO> getBatteryDetails(List<Long> postCodes);
}
