package com.almacen.ms_proveedores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.ms_proveedores.model.Proveedor;
import com.almacen.ms_proveedores.repository.ProveedorRepository;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;
    
    
     //metodo crear--------------------------------------
    public Proveedor crear(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    //metodo listar------------------------------------------------------------

    public List<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    //metodo buscar por id------------------
    public Proveedor obtener(Long id) {
        return proveedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    //metodo actualizar datos del cliente (deuda, limite para fiar, etc)--------------------------------
    public Proveedor actualizar(Long id, Proveedor actualizado) {
        Proveedor existe = obtener(id);
        existe.setNombreEmpresa(actualizado.getNombreEmpresa());
        existe.setNombreContacto(actualizado.getNombreContacto());
        existe.setTelefono(actualizado.getTelefono());
        existe.setCategoriaRubro(actualizado.getCategoriaRubro());
        return proveedorRepository.save(existe);
    }

    //metodo eliminar-----------------------------------------
    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}
