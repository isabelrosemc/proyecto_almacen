package com.almacen.ms_proveedores.service.impl;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.almacen.ms_proveedores.dto.ProveedorRequestDTO;
import com.almacen.ms_proveedores.dto.ProveedorResponseDTO;
import com.almacen.ms_proveedores.exception.DuplicateProveedorException;
import com.almacen.ms_proveedores.exception.ProveedorNotFoundException;
import com.almacen.ms_proveedores.mapper.ProveedorMapper;
import com.almacen.ms_proveedores.model.Proveedor;
import com.almacen.ms_proveedores.repository.ProveedorRepository;
import com.almacen.ms_proveedores.service.ProveedorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Override
    public ProveedorResponseDTO crearProveedor(
            ProveedorRequestDTO request
    ) {

        log.info("Creando proveedor: {}", request.getRazonSocial());

        if (proveedorRepository.existsByRut(request.getRut())) {
            throw new DuplicateProveedorException(
                    "El RUT ya existe"
            );
        }

        if (proveedorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateProveedorException(
                    "El email ya existe"
            );
        }

        Proveedor proveedor =
                ProveedorMapper.toEntity(request);

        Proveedor guardado =
                proveedorRepository.save(proveedor);

        log.info("Proveedor creado correctamente ID: {}",
                guardado.getId());

        return ProveedorMapper.toDTO(guardado);
    }

    @Override
    public List<ProveedorResponseDTO> listarProveedores() {

        log.info("Listando proveedores");

        return proveedorRepository.findAll()
                .stream()
                .map(ProveedorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorResponseDTO buscarPorId(Long id) {

        log.info("Buscando proveedor ID: {}", id);

        Proveedor proveedor =
                proveedorRepository.findById(id)
                        .orElseThrow(() ->
                                new ProveedorNotFoundException(
                                        "Proveedor no encontrado"
                                ));

        return ProveedorMapper.toDTO(proveedor);
    }

    @Override
    public ProveedorResponseDTO actualizarProveedor(
            Long id,
            ProveedorRequestDTO request
    ) {

        log.info("Actualizando proveedor ID: {}", id);

        Proveedor proveedor =
                proveedorRepository.findById(id)
                        .orElseThrow(() ->
                                new ProveedorNotFoundException(
                                        "Proveedor no encontrado"
                                ));

        proveedor.setRazonSocial(request.getRazonSocial());
        proveedor.setRut(request.getRut());
        proveedor.setEmail(request.getEmail());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setEstado(request.getEstado());

        Proveedor actualizado =
                proveedorRepository.save(proveedor);

        log.info("Proveedor actualizado correctamente");

        return ProveedorMapper.toDTO(actualizado);
    }

    @Override
    public void eliminarProveedor(Long id) {

        log.info("Eliminando proveedor ID: {}", id);

        Proveedor proveedor =
                proveedorRepository.findById(id)
                        .orElseThrow(() ->
                                new ProveedorNotFoundException(
                                        "Proveedor no encontrado"
                                ));

        proveedorRepository.delete(proveedor);

        log.info("Proveedor eliminado correctamente");
    }
}