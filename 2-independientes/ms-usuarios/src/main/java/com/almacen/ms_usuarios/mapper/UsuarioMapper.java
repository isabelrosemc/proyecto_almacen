package com.almacen.ms_usuarios.mapper;

import org.springframework.stereotype.Component;

import com.almacen.ms_usuarios.dto.UsuarioResponseDTO;
import com.almacen.ms_usuarios.model.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toDTO(Usuario usuario) {

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .estado(usuario.getEstado())
                .rol(usuario.getRol().name())
                .fechaCreacion(usuario.getFechaCreacion())
                .build();
    }
}