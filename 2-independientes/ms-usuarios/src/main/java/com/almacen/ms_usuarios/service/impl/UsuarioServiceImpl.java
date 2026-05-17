package com.almacen.ms_usuarios.service.impl;

import com.almacen.ms_usuarios.dto.UsuarioRequestDTO;
import com.almacen.ms_usuarios.dto.UsuarioResponseDTO;
import com.almacen.ms_usuarios.exception.DuplicateResourceException;
import com.almacen.ms_usuarios.exception.ResourceNotFoundException;
import com.almacen.ms_usuarios.mapper.UsuarioMapper;
import com.almacen.ms_usuarios.model.Usuario;
import com.almacen.ms_usuarios.repository.UsuarioRepository;
import com.almacen.ms_usuarios.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {

        log.info("Intentando crear usuario con email: {}", request.getEmail());

        if (usuarioRepository.existsByEmail(request.getEmail())) {

            log.error("El email ya existe: {}", request.getEmail());

            throw new DuplicateResourceException(
                    "El email ya existe"
            );
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .estado(request.getEstado())
                .rol(request.getRol())
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        log.info("Usuario creado correctamente con ID: {}",
                usuarioGuardado.getId());

        return usuarioMapper.toDTO(usuarioGuardado);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {

        log.info("Listando usuarios");

        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {

        log.info("Buscando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado"
                        )
                );

        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorEmail(String email) {

        log.info("Buscando usuario con email: {}", email);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado"
                        )
                );

        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizarUsuario(
            Long id,
            UsuarioRequestDTO request
    ) {

        log.info("Actualizando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado"
                        )
                );

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setEstado(request.getEstado());
        usuario.setRol(request.getRol());

        Usuario usuarioActualizado =
                usuarioRepository.save(usuario);

        log.info("Usuario actualizado correctamente");

        return usuarioMapper.toDTO(usuarioActualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {

        log.info("Eliminando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado"
                        )
                );

        usuarioRepository.delete(usuario);

        log.info("Usuario eliminado correctamente");
    }
}