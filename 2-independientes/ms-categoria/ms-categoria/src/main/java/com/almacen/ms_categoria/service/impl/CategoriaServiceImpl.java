package com.almacen.ms_categoria.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.almacen.ms_categoria.dto.CategoriaRequestDTO;
import com.almacen.ms_categoria.dto.CategoriaResponseDTO;
import com.almacen.ms_categoria.exception.CategoriaNotFoundException;
import com.almacen.ms_categoria.exception.DuplicateCategoriaException;
import com.almacen.ms_categoria.mapper.CategoriaMapper;
import com.almacen.ms_categoria.model.Categoria;
import com.almacen.ms_categoria.repository.CategoriaRepository;
import com.almacen.ms_categoria.service.CategoriaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO request) {

        log.info("Intentando crear categoria: {}", request.getNombre());

        // 🔴 validación de duplicados (usando tu método real)
        if (categoriaRepository.findByNombreIgnoreCase(request.getNombre()).isPresent()) {
            throw new DuplicateCategoriaException("La categoria ya existe");
        }

        Categoria categoria = CategoriaMapper.toEntity(request);

        Categoria guardada = categoriaRepository.save(categoria);

        log.info("Categoria creada correctamente con ID: {}", guardada.getId());

        return CategoriaMapper.toDTO(guardada);
    }

    @Override
    public List<CategoriaResponseDTO> listarCategorias() {

        log.info("Listando categorias");

        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponseDTO buscarPorId(Long id) {

        log.info("Buscando categoria con ID: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));

        return CategoriaMapper.toDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO request) {

        log.info("Actualizando categoria con ID: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));

        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        categoria.setEstado(request.getEstado());

        Categoria actualizada = categoriaRepository.save(categoria);

        log.info("Categoria actualizada correctamente");

        return CategoriaMapper.toDTO(actualizada);
    }

    @Override
    public void eliminarCategoria(Long id) {

        log.info("Eliminando categoria con ID: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));

        categoriaRepository.delete(categoria);

        log.info("Categoria eliminada correctamente");
    }
}