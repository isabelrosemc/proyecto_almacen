package com.almacen.ms_categoria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.ms_categoria.model.Categoria;
import com.almacen.ms_categoria.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // CREAR
    public Categoria crear(Categoria categoria) {
        
        // No pueden existir dos categorías con el mismo nombre (ej: "Bebidas" y "bebidas")
        if (categoriaRepository.findByNombreIgnoreCase(categoria.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe una categoría llamada: " + categoria.getNombre());
        }

        return categoriaRepository.save(categoria);
    }

    // LISTAR
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    // OBTENER POR ID
    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe la categoría con el ID: " + id));
    }

    // OBTENER POR NOMBRE 
    public Categoria obtenerPorNombre(String nombre) {
        return categoriaRepository.findByNombreIgnoreCase(nombre)
            .orElseThrow(() -> new RuntimeException("No se encontró la categoría: " + nombre));
    }

    // ACTUALIZAR
    public Categoria actualizar(Long id, Categoria categoriaActualizada) {
        
        Categoria existe = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe la categoría para actualizar"));

        if (!existe.getNombre().equalsIgnoreCase(categoriaActualizada.getNombre())) {
            if (categoriaRepository.findByNombreIgnoreCase(categoriaActualizada.getNombre()).isPresent()) {
                throw new RuntimeException("Ese nombre de categoría ya está en uso");
            }
        }

        existe.setNombre(categoriaActualizada.getNombre());
        existe.setDescripcion(categoriaActualizada.getDescripcion());

        return categoriaRepository.save(existe);
    }

    // ELIMINAR
    public void eliminar(Long id) {
        
        Categoria existe = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe la categoría para eliminar"));

        categoriaRepository.delete(existe);
    }
}


