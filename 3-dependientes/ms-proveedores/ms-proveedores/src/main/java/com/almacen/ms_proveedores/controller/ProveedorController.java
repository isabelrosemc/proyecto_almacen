package com.almacen.ms_proveedores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almacen.ms_proveedores.model.Proveedor;
import com.almacen.ms_proveedores.service.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Crear
    @PostMapping
    public Proveedor crear(@RequestBody Proveedor proveedor) {
        return proveedorService.crear(proveedor);
    }

    // Listar todos
    @GetMapping
    public List<Proveedor> listar(){
        return proveedorService.listar();
    }

    // Lista por categoria
    @GetMapping("/categoria/{categoriaRubro}")
    public List<Proveedor> listaPorCategoriaRubro(@PathVariable String categoriaRubro) {
        return proveedorService.listarPorCategoriaRubro(categoriaRubro);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Proveedor obtener(@PathVariable Long id) {
        return proveedorService.obtener(id);
    }

    // Actualizar
    @PutMapping("{id}")
    public Proveedor actualizar(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        return proveedorService.actualizar(id, proveedor);
    }

    // Eliminar
    @DeleteMapping("{id}")
    public void eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
    }

}
