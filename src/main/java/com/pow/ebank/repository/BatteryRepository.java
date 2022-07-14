package com.pow.ebank.repository;

import com.pow.ebank.model.Battery;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends PagingAndSortingRepository<Battery, Long> {

    List<Battery> findBatteriesByLocationPostCodeIn(List<Long> locations);
}
