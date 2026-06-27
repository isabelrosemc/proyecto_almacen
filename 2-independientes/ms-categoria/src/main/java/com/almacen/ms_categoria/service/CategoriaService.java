package com.almacen.ms_categoria.service;

import java.util.List;

import com.almacen.ms_categoria.dto.CategoriaRequestDTO;
import com.almacen.ms_categoria.dto.CategoriaResponseDTO;


public interface CategoriaService {

    CategoriaResponseDTO crearCategoria(CategoriaRequestDTO request);

    List<CategoriaResponseDTO> listarCategorias();

    CategoriaResponseDTO buscarPorId(Long id);

    CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO request);

    void eliminarCategoria(Long id);
}

