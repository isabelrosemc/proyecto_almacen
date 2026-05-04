package com.almacen.ms_proveedores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.almacen.ms_proveedores.model.Proveedor;
import com.almacen.ms_proveedores.repository.ProveedorRepository;

@Service
@Transactional
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    // CREAR
    public Proveedor crear(Proveedor proveedor) {

        // validar nombreEmpresa obligatorio
        if (proveedor.getNombreEmpresa() == null || proveedor.getNombreEmpresa().isBlank()) {
            throw new RuntimeException("El nombre de la empresa es obligatorio");
        }

        // validar duplicado
        if (proveedorRepository.findAll()
                .stream()
                .anyMatch(p -> p.getNombreEmpresa().equalsIgnoreCase(proveedor.getNombreEmpresa()))) {
            throw new RuntimeException("Ya existe un proveedor con ese nombre de empresa");
        }

        // validar nombreContacto
        if (proveedor.getNombreContacto() == null || proveedor.getNombreContacto().isBlank()) {
            throw new RuntimeException("El nombre de contacto es obligatorio");
        }

        // validar telefono
        if (proveedor.getTelefono() == null || proveedor.getTelefono().isBlank()) {
            throw new RuntimeException("El telefono es obligatorio");
        }

        // validar categoriaRubro
        if (proveedor.getCategoriaRubro() == null || proveedor.getCategoriaRubro().isBlank()) {
            throw new RuntimeException("La categoria de rubro es obligatoria");
        }

        return proveedorRepository.save(proveedor);
    }

    // LISTAR
    public List<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    // OBTENER POR ID
    public Proveedor obtener(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    // ACTUALIZAR
    public Proveedor actualizar(Long id, Proveedor actualizado) {

        Proveedor existe = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        // validar nombreEmpresa obligatorio
        if (actualizado.getNombreEmpresa() == null || actualizado.getNombreEmpresa().isBlank()) {
            throw new RuntimeException("El nombre de la empresa es obligatorio");
        }

        // validar nombreContacto
        if (actualizado.getNombreContacto() == null || actualizado.getNombreContacto().isBlank()) {
            throw new RuntimeException("El nombre de contacto es obligatorio");
        }

        // validar telefono
        if (actualizado.getTelefono() == null || actualizado.getTelefono().isBlank()) {
            throw new RuntimeException("El telefono es obligatorio");
        }

        // validar categoriaRubro
        if (actualizado.getCategoriaRubro() == null || actualizado.getCategoriaRubro().isBlank()) {
            throw new RuntimeException("La categoria de rubro es obligatoria");
        }

        // actualizar campos
        existe.setNombreEmpresa(actualizado.getNombreEmpresa());
        existe.setNombreContacto(actualizado.getNombreContacto());
        existe.setTelefono(actualizado.getTelefono());
        existe.setCategoriaRubro(actualizado.getCategoriaRubro());

        return proveedorRepository.save(existe);
    }

    // ELIMINAR
    public void eliminar(Long id) {

        Proveedor existe = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        proveedorRepository.delete(existe);
    }

    // LISTAR POR CATEGORIA RUBRO
    public List<Proveedor> listarPorCategoriaRubro(String categoriaRubro) {
        return proveedorRepository.findByCategoriaRubro(categoriaRubro);
    }
    
}