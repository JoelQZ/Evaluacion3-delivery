package com.tecsup.delivery.msrepartidor.controller;

import com.tecsup.delivery.msrepartidor.dto.RepartidorDTO;
import com.tecsup.delivery.msrepartidor.model.Repartidor;
import com.tecsup.delivery.msrepartidor.service.RepartidorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/repartidores")
public class RepartidorController {
    private final RepartidorService service;

    public RepartidorController(RepartidorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Repartidor> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Repartidor findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Repartidor> create(@Valid @RequestBody RepartidorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public Repartidor update(@PathVariable Long id, @Valid @RequestBody RepartidorDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
