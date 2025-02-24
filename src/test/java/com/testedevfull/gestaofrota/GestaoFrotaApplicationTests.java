package com.testedevfull.gestaofrota;

import com.agromercantil.gestaofrota.client.FipeClient;
import com.agromercantil.gestaofrota.integration.dto.FipePrice;
import com.agromercantil.gestaofrota.model.Truck;
import com.agromercantil.gestaofrota.repository.TruckRepository;
import com.agromercantil.gestaofrota.service.TruckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TruckServiceTest {

	@Mock
	private TruckRepository truckRepository;

	@Mock
	private FipeClient fipeClient;

	@InjectMocks
	private TruckService truckService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRegisterTruck() {
		// Dados de entrada
		Truck truck = new Truck();
		truck.setLicensePlate("ABC-1234");
		truck.setBrand("Volvo");
		truck.setModel("FH 540");
		truck.setManufacturingYear(2020);

		// Mock do retorno da API da FIPE
		FipePrice fipePrice = new FipePrice();
		fipePrice.setPrice("R$ 500.000,00");
		when(fipeClient.getPrice(anyString(), anyString(), anyString())).thenReturn(fipePrice);

		// Mock do comportamento do repositório
		when(truckRepository.findByLicensePlate(truck.getLicensePlate())).thenReturn(Optional.empty());
		when(truckRepository.save(any(Truck.class))).thenReturn(truck);

		// Execução do método a ser testado
		Truck result = truckService.registerTruck(truck);

		// Verificações
		assertNotNull(result);
		assertEquals(500000.00, result.getFipePrice());
		verify(truckRepository, times(1)).save(truck);
		verify(fipeClient, times(1)).getPrice(anyString(), anyString(), anyString());
	}
}