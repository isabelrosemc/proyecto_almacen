package com.almacen.ms_usuarios.service.impl;

import com.almacen.ms_usuarios.dto.UsuarioRequestDTO;
import com.almacen.ms_usuarios.dto.UsuarioResponseDTO;
import com.almacen.ms_usuarios.exception.ResourceNotFoundException;
import com.almacen.ms_usuarios.model.Usuario;
import com.almacen.ms_usuarios.repository.UsuarioRepository;
import com.almacen.ms_usuarios.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {

        log.info("Creando usuario con correo: {}", dto.getCorreo());

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .password(dto.getPassword())
                .rol(dto.getRol())
                .activo(true)
                .build();

        Usuario guardado = repository.save(usuario);

        log.info("Usuario creado con ID: {}", guardado.getId());

        return mapToResponse(guardado);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {

        log.info("Listando usuarios");

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UsuarioResponseDTO obtenerUsuario(Long id) {

        log.info("Buscando usuario ID: {}", id);

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado"));

        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {

        log.info("Actualizando usuario ID: {}", id);

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());

        Usuario actualizado = repository.save(usuario);

        log.info("Usuario actualizado ID: {}", actualizado.getId());

        return mapToResponse(actualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {

        log.info("Eliminando usuario ID: {}", id);

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado"));

        repository.delete(usuario);

        log.info("Usuario eliminado ID: {}", id);
    }

    private UsuarioResponseDTO mapToResponse(Usuario usuario) {

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol())
                .activo(usuario.getActivo())
                .build();
    }

    @Override
    public UsuarioResponseDTO obtenerPorCorreo(String correo) {

        log.info("Buscando usuario por correo: {}", correo);

        Usuario usuario = repository.findByCorreo(correo)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado"));

        return mapToResponse(usuario);
}
}