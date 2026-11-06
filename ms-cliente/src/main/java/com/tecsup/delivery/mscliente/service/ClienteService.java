package com.tecsup.delivery.mscliente.service;

import com.tecsup.delivery.mscliente.dto.ClienteDTO;
import com.tecsup.delivery.mscliente.exception.ResourceNotFoundException;
import com.tecsup.delivery.mscliente.model.Cliente;
import com.tecsup.delivery.mscliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + id));
    }

    public Cliente create(ClienteDTO dto) {
        return repository.save(toEntity(dto, new Cliente()));
    }

    public Cliente update(Long id, ClienteDTO dto) {
        return repository.save(toEntity(dto, findById(id)));
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    private Cliente toEntity(ClienteDTO dto, Cliente cliente) {
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        return cliente;
    }
}
