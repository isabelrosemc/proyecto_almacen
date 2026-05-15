package com.almacen.ms_clientes.service.impl;

import com.almacen.ms_clientes.dto.ClienteRequestDTO;
import com.almacen.ms_clientes.dto.ClienteResponseDTO;
import com.almacen.ms_clientes.exception.ClienteNotFoundException;
import com.almacen.ms_clientes.exception.DuplicateClienteException;
import com.almacen.ms_clientes.mapper.ClienteMapper;
import com.almacen.ms_clientes.model.Cliente;
import com.almacen.ms_clientes.repository.ClienteRepository;
import com.almacen.ms_clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;

    @Override
    public ClienteResponseDTO crear(ClienteRequestDTO dto) {

        log.info("Creando cliente {}", dto.getEmail());

        if (repository.existsByEmail(dto.getEmail())) {
            throw new DuplicateClienteException(
                    "Ya existe un cliente con ese email"
            );
        }

        Cliente cliente = ClienteMapper.toEntity(dto);

        Cliente guardado = repository.save(cliente);

        log.info("Cliente creado correctamente ID {}", guardado.getId());

        return ClienteMapper.toDTO(guardado);
    }

    @Override
    public List<ClienteResponseDTO> listar() {

        log.info("Listando clientes");

        return repository.findAll()
                .stream()
                .map(ClienteMapper::toDTO)
                .toList();
    }

    @Override
    public ClienteResponseDTO buscarPorId(Long id) {

        log.info("Buscando cliente ID {}", id);

        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new ClienteNotFoundException(
                                "Cliente no encontrado con ID: " + id
                        )
                );

        return ClienteMapper.toDTO(cliente);
    }

    @Override
    public ClienteResponseDTO actualizar(Long id,
                                         ClienteRequestDTO dto) {

        log.info("Actualizando cliente ID {}", id);

        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new ClienteNotFoundException(
                                "Cliente no encontrado con ID: " + id
                        )
                );

        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setEstado(dto.getEstado());

        Cliente actualizado = repository.save(cliente);

        log.info("Cliente actualizado correctamente");

        return ClienteMapper.toDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando cliente ID {}", id);

        Cliente cliente = repository.findById(id)
                .orElseThrow(() ->
                        new ClienteNotFoundException(
                                "Cliente no encontrado con ID: " + id
                        )
                );

        repository.delete(cliente);

        log.info("Cliente eliminado correctamente");
    }
}