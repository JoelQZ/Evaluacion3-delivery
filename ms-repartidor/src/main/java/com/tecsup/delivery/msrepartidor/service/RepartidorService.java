package com.tecsup.delivery.msrepartidor.service;

import com.tecsup.delivery.msrepartidor.dto.RepartidorDTO;
import com.tecsup.delivery.msrepartidor.exception.ResourceNotFoundException;
import com.tecsup.delivery.msrepartidor.model.Repartidor;
import com.tecsup.delivery.msrepartidor.repository.RepartidorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RepartidorService {
    private final RepartidorRepository repository;

    public RepartidorService(RepartidorRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Repartidor> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Repartidor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repartidor no encontrado: " + id));
    }

    public Repartidor create(RepartidorDTO dto) {
        return repository.save(toEntity(dto, new Repartidor()));
    }

    public Repartidor update(Long id, RepartidorDTO dto) {
        return repository.save(toEntity(dto, findById(id)));
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    private Repartidor toEntity(RepartidorDTO dto, Repartidor repartidor) {
        repartidor.setNombre(dto.getNombre());
        repartidor.setTelefono(dto.getTelefono());
        repartidor.setVehiculo(dto.getVehiculo());
        repartidor.setDisponible(dto.getDisponible());
        return repartidor;
    }
}
