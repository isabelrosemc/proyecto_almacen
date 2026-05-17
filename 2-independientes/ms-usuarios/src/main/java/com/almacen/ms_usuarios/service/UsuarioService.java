package com.almacen.ms_usuarios.service;

import com.almacen.ms_usuarios.dto.UsuarioRequestDTO;
import com.almacen.ms_usuarios.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto);

    List<UsuarioResponseDTO> listarUsuarios();

    UsuarioResponseDTO obtenerUsuario(Long id);

    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto);

    UsuarioResponseDTO obtenerPorCorreo(String correo);

    void eliminarUsuario(Long id);
}