package com.agromercantil.gestaofrota.controller;

import com.agromercantil.gestaofrota.model.Truck;
import com.agromercantil.gestaofrota.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {

    @Autowired
    private TruckService truckService;

    //cadastrar um novo caminhão
    @PostMapping
    public ResponseEntity<Truck> createTruck(@RequestBody Truck truck) {

        Truck createdTruck = truckService.registerTruck(truck);;
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTruck);
    }

    // Listar todos os caminhões
    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {
        List<Truck> trucks = truckService.getAllTrucks();
        return ResponseEntity.ok(trucks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Truck> updateTruck(@PathVariable Long id, @RequestBody Truck truckDetails) {
        Truck updatedTruck = truckService.updateTruck(id, truckDetails);
        return ResponseEntity.ok(updatedTruck);
    }

    // Deletar um caminhão com base no seu ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        try {
            truckService.deleteTruck(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}