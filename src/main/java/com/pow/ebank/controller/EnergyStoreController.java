package com.pow.ebank.controller;

import com.pow.ebank.dto.BatteryDTO;
import com.pow.ebank.dto.BatteryResponseDTO;
import com.pow.ebank.service.EnergyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/energy")
@Slf4j
public class EnergyStoreController {

    @Autowired
    private EnergyService energyService;

    @PostMapping("/test")
    public List<BatteryDTO> sayHello(){
        List<BatteryDTO> batteryDTOList = new ArrayList<>();
        log.info("Requst : {}",batteryDTOList);
        return batteryDTOList;
    }

    @PostMapping("/batteries/add")
    public ResponseEntity<List<BatteryDTO>> addBatteries(@RequestBody List<BatteryDTO> batteries) {
        log.info("Requst [{}]",batteries);
        List<BatteryDTO> batteryDTOList = energyService.save(batteries);
        return new ResponseEntity<>(batteryDTOList, HttpStatus.OK);
    }

    @GetMapping("/batteries")
    public ResponseEntity<BatteryResponseDTO> getBatteryList(@RequestParam("postCodes") List<Long> postCodes){
        Optional<BatteryResponseDTO> batteryDetails = energyService.getBatteryDetails(postCodes);
        return new ResponseEntity<>(batteryDetails.get(), HttpStatus.OK);
    }
}
