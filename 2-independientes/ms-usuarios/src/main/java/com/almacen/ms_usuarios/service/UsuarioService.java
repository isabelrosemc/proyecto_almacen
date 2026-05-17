package com.almacen.ms_usuarios.service;

import com.almacen.ms_usuarios.dto.UsuarioRequestDTO;
import com.almacen.ms_usuarios.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request);

    List<UsuarioResponseDTO> listarUsuarios();

    UsuarioResponseDTO obtenerUsuarioPorId(Long id);

    UsuarioResponseDTO obtenerUsuarioPorEmail(String email);

    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO request);

    void eliminarUsuario(Long id);
}