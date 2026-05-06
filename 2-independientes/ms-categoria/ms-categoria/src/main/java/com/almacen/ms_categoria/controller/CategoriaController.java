package com.almacen.ms_categoria.controller;

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

import com.almacen.ms_categoria.model.Categoria;
import com.almacen.ms_categoria.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")

public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // Crear
    @PostMapping
    public Categoria crear(@Valid @RequestBody Categoria categoria){
        return categoriaService.crear(categoria);
    }

    // Listar todos
    @GetMapping
    public List<Categoria> listar(){
        return categoriaService.listar();
    }

    // Buscar por id
    @GetMapping("/{id}")
    public Categoria obtenerPorId(@PathVariable Long id){
        return categoriaService.obtenerPorId(id);
    }

    // Actualizar
    @PutMapping("/{id}")
    public Categoria actualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria){
        return categoriaService.actualizar(id, categoria);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        categoriaService.eliminar(id);
    }

        // Metodo custom (Obtener por nombre)
    @GetMapping("/nombre/{nombre}")
    public Categoria listarNombre(@PathVariable String nombre){
        return categoriaService.obtenerPorNombre(nombre);
    }

}
