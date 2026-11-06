package com.tecsup.delivery.msentrega.controller;

import com.tecsup.delivery.msentrega.dto.EntregaDTO;
import com.tecsup.delivery.msentrega.model.Entrega;
import com.tecsup.delivery.msentrega.model.EstadoEntrega;
import com.tecsup.delivery.msentrega.service.EntregaService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
public class EntregaController {
    private final EntregaService service;

    public EntregaController(EntregaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Entrega> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Entrega findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Entrega> create(@Valid @RequestBody EntregaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntregaDTO> actualizarEntrega(@PathVariable Long id,
                                                         @Valid @RequestBody EntregaDTO dto) {
        return ResponseEntity.ok(service.actualizarEntrega(id, dto));
    }

    @PutMapping("/{id}/estado")
    public Entrega updateStatus(@PathVariable Long id, @RequestParam EstadoEntrega nuevoEstado) {
        return service.updateStatus(id, nuevoEstado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
