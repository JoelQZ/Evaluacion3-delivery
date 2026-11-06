package com.tecsup.delivery.mscliente.repository;

import com.tecsup.delivery.mscliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
