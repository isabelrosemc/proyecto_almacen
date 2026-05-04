package com.almacen.ms_proveedores.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.ms_proveedores.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long>{

    Optional<Proveedor> findByNombreEmpresaIgnoreCase(String nombreEmpresa);
    
    // Listar proveedores por rubro Bebidas, Lacteos, Abarrotes, etc
    List<Proveedor> findByCategoriaRubro(String rubro);
}
