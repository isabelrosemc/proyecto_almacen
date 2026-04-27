package com.almacen.ms_clientes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.ms_clientes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    Optional<Cliente> findByApodoIgnoreCase(String apodo);

}
