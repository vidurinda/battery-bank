package com.pow.ebank.service;

import com.pow.ebank.dto.BatteryDTO;
import com.pow.ebank.dto.BatteryResponseDTO;
import com.pow.ebank.exception.InvalidParameterException;
import com.pow.ebank.exception.ValueNotFoundException;
import com.pow.ebank.model.Battery;
import com.pow.ebank.model.Location;
import com.pow.ebank.repository.BatteryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.hibernate.JDBCException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnergyServiceImpl implements EnergyService{

    @Autowired
    private BatteryRepository batteryRepository;

    @Override
    @Transactional
    public List<BatteryDTO> save(List<BatteryDTO> batteries) {
        List<BatteryDTO> result = new ArrayList<>();
        List<Battery> batteryList = batteries.stream()
                .peek(batteryDTO -> {
                    if(StringUtils.isEmpty(batteryDTO.getName()) || StringUtils.isEmpty(batteryDTO.getPostCode())){
                        throw new InvalidParameterException("Required parameters are empty");
                    }})
                .map(this::convertToEntity)
                .collect(Collectors.toCollection(ArrayList::new));
        try {

            Iterable<Battery> batteryIterable = batteryRepository.saveAll(batteryList);
            batteryIterable.forEach(batteryList::add);

            return batteryList.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toCollection(ArrayList::new));

        } catch (JDBCException e){
            log.error("Something went wrong when saving battery details {} ", e);
            throw new RuntimeException("Something went wrong when saving battery details");
        }
    }

    @Override
    public List<BatteryDTO> getBatteryList(List<Long> postCodes) {
        List<Battery> batteries = batteryRepository.findBatteriesByLocationPostCodeIn(postCodes);
        if(batteries.isEmpty()){
            throw new ValueNotFoundException("Requested results not found");
        }

        return batteries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Optional<BatteryResponseDTO> getBatteryDetails(List<Long> postCodes) {

        try{
            List<Battery> batteries = batteryRepository.findBatteriesByLocationPostCodeIn(postCodes);
            if(batteries.isEmpty()){
                throw new ValueNotFoundException("Requested results not found");
            }

            DoubleSummaryStatistics summaryStatistics = batteries.stream()
                    .mapToDouble(Battery::getWattCapacity)
                    .summaryStatistics();
            List<String> names = batteries.stream()
                    .map(Battery::getName)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            return Optional.ofNullable(BatteryResponseDTO.builder()
                    .names(names)
                    .averageWattCapacity(BigDecimal.valueOf(summaryStatistics.getAverage()))
                    .totalWattCapacity(BigDecimal.valueOf(summaryStatistics.getSum()))
                    .build());
        } catch (JDBCException e){
            log.error("Error processing battery details");
        } 
        return Optional.empty();
    }


    private BatteryDTO convertToDto(Battery battery){
        return BatteryDTO.builder()
                .name(battery.getName())
                .wattCapacity(battery.getWattCapacity())
                .id(battery.getBatteryId())
                .postCode(battery.getLocation().getPostCode())
                .build();

    }

    private Battery convertToEntity(BatteryDTO batteryDTO){
        return Battery.builder()
                .name(batteryDTO.getName())
                .wattCapacity(batteryDTO.getWattCapacity())
                .location(
                        Location.builder()
                                .postCode(batteryDTO.getPostCode())
                                .build())
                .build();
    }
}
