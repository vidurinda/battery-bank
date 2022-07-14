package com.pow.ebank;

import com.pow.ebank.dto.BatteryDTO;
import com.pow.ebank.dto.BatteryResponseDTO;
import com.pow.ebank.model.Battery;
import com.pow.ebank.model.Location;
import com.pow.ebank.repository.BatteryRepository;
import com.pow.ebank.service.EnergyServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class EnergyBankApplicationTests {

	@InjectMocks
	EnergyServiceImpl energyService;

	@Mock
	BatteryRepository batteryRepository;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void saveBatteriesTest(){
		BatteryDTO b1 = BatteryDTO.builder()
				.name("Test1").wattCapacity(60)
						.postCode(111L).build();

		BatteryDTO b2 = BatteryDTO.builder()
				.name("Test2").wattCapacity(45)
				.postCode(222L).build();
		Mockito.when(energyService.save(Mockito.anyList())).thenReturn(List.of(b1,b2));
	}


	@Test
	void getBatteryDetails(){

		Battery b1 = Battery.builder()
				.name("Test1").wattCapacity(60)
				.location(Location.builder().postCode(111L).locationName("AA").build())
				.build();
		Battery b2 = Battery.builder()
				.name("Test2").wattCapacity(60)
				.location(Location.builder().postCode(222L).locationName("BB").build())
				.build();

		Mockito.when(batteryRepository.saveAll(Mockito.anyList())).thenReturn(List.of(b1,b2));
		Mockito.when(batteryRepository.findBatteriesByLocationPostCodeIn(Mockito.anyList())).thenReturn(List.of(b1,b2));

		Optional<BatteryResponseDTO> responseDTO = energyService.getBatteryDetails(List.of(222L, 111L));

		Assert.assertEquals(responseDTO.get().getAverageWattCapacity(), BigDecimal.valueOf(60.0));
		Assert.assertEquals(responseDTO.get().getTotalWattCapacity(), BigDecimal.valueOf(120.0));
		Assert.assertEquals(responseDTO.get().getNames().size(),2);

	}

}
