package com.almacen.ms_usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.ms_usuarios.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}