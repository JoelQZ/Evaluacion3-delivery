package com.tecsup.delivery.msentrega.service;

import com.tecsup.delivery.msentrega.dto.EntregaDTO;
import com.tecsup.delivery.msentrega.exception.ResourceNotFoundException;
import com.tecsup.delivery.msentrega.model.Entrega;
import com.tecsup.delivery.msentrega.model.EstadoEntrega;
import com.tecsup.delivery.msentrega.repository.EntregaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EntregaService {
    private final EntregaRepository repository;

    public EntregaService(EntregaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Entrega> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Entrega findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega no encontrada: " + id));
    }

    public Entrega create(EntregaDTO dto) {
        Entrega entrega = new Entrega();
        entrega.setIdCliente(dto.getIdCliente());
        entrega.setIdRepartidor(dto.getIdRepartidor());
        entrega.setDireccion(dto.getDireccion());
        entrega.setCosto(dto.getCosto());
        entrega.setFecha(dto.getFecha() == null ? LocalDateTime.now() : dto.getFecha());
        entrega.setEstado(EstadoEntrega.PENDIENTE);
        return repository.save(entrega);
    }

    public EntregaDTO actualizarEntrega(Long id, EntregaDTO dto) {
        Entrega entrega = findById(id);
        entrega.setIdCliente(dto.getIdCliente());
        entrega.setIdRepartidor(dto.getIdRepartidor());
        entrega.setDireccion(dto.getDireccion());
        entrega.setCosto(dto.getCosto());
        if (dto.getFecha() != null) {
            entrega.setFecha(dto.getFecha());
        }
        if (dto.getEstado() != null) {
            entrega.setEstado(dto.getEstado());
        }

        return toDTO(repository.save(entrega));
    }

    public Entrega updateStatus(Long id, EstadoEntrega nuevoEstado) {
        Entrega entrega = findById(id);
        entrega.setEstado(nuevoEstado);
        return repository.save(entrega);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    private EntregaDTO toDTO(Entrega entrega) {
        EntregaDTO dto = new EntregaDTO();
        dto.setIdCliente(entrega.getIdCliente());
        dto.setIdRepartidor(entrega.getIdRepartidor());
        dto.setDireccion(entrega.getDireccion());
        dto.setFecha(entrega.getFecha());
        dto.setCosto(entrega.getCosto());
        dto.setEstado(entrega.getEstado());
        return dto;
    }
}
