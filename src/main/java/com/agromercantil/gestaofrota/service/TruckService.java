package com.agromercantil.gestaofrota.service;

import com.agromercantil.gestaofrota.client.FipeClient;
import com.agromercantil.gestaofrota.integration.dto.FipePrice;
import com.agromercantil.gestaofrota.model.Truck;
import com.agromercantil.gestaofrota.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private FipeClient fipeClient;

    @Transactional
    public Truck registerTruck(Truck truck) {
        // Verifica se a placa já está cadastrada
        if (truckRepository.findByLicensePlate(truck.getLicensePlate()).isPresent()) {
            throw new IllegalArgumentException("Placa já cadastrada.");
        }

        // Obtém todas as marcas de caminhões
        List<Marcas> marcas = fipeClient.getBrands();

        // Encontra a marca correspondente
        Marca marca = marcas.stream()
                .filter(m -> m.getNome().equalsIgnoreCase(truck.getBrand()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Marca inválida."));

        // Obtém os modelos para a marca encontrada
        ModelsResponse modelsResponse = fipeClient.getModels(marca.marcaId());

        // Encontra o modelo correspondente
        Modelo modelo = modelsResponse.getModels().stream()
                .filter(m -> m.getNome().equalsIgnoreCase(truck.getModel()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Modelo inválido."));

        // Formata o ano de fabricação
        String anoFormatado = String.format("%d-1", truck.getManufacturingYear());

        // Obtém o preço FIPE
        FipePrice fipePrice = fipeClient.getPrice(marca.getmarcaId(), modelo.getmodeloId(), anoFormatado);
        if (fipePrice == null || fipePrice.getPrice() == null) {
            throw new IllegalArgumentException("Não foi possível obter o preço FIPE para o caminhão especificado.");
        }

        // Define o preço FIPE no objeto Truck
        truck.setFipePrice(Double.parseDouble(fipePrice.getPrice().replace("R$", "").replace(",", "").trim()));

        // Salva o caminhão no repositório
        return truckRepository.save(truck);
    }

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    public Truck updateTruck(Long id, TruckUpdateDTO truckUpdateDTO) {
        Optional<Truck> optionalTruck = truckRepository.findById(id);
        if (optionalTruck.isPresent()) {
            Truck truck = optionalTruck.get();
            truck.setBrand(truckUpdateDTO.getBrand());
            truck.setModel(truckUpdateDTO.getModel());

            return truckRepository.save(truck);

        } else {
            throw new IllegalArgumentException("Caminhão não encontrado.");
        }
    }

    @Transactional
    public void deleteTruck(Long id) {
        if (truckRepository.existsById(id)) {
            truckRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Caminhão não encontrado.");
        }
    }
}