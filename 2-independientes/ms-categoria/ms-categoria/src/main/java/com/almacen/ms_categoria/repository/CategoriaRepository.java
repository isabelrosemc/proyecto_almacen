package com.almacen.ms_categoria.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.almacen.ms_categoria.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Para evitar duplicados como "Bebidas" y "bebidas"
    Optional<Categoria> findByNombreIgnoreCase(String nombre);

}