package com.almacen.ms_proveedores.repository;

import com.ms.proveedores.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    boolean existsByRut(String rut);

    boolean existsByEmail(String email);
}