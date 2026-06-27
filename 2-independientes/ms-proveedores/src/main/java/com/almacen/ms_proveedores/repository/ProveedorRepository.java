package com.almacen.ms_proveedores.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.ms_proveedores.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    boolean existsByRut(String rut);

    boolean existsByEmail(String email);
}