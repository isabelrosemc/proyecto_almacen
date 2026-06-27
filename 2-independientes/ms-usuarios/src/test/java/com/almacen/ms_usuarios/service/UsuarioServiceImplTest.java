package com.almacen.ms_usuarios.service;

import com.almacen.ms_usuarios.dto.UsuarioRequestDTO;
import com.almacen.ms_usuarios.dto.UsuarioResponseDTO;
import com.almacen.ms_usuarios.exception.DuplicateResourceException;
import com.almacen.ms_usuarios.exception.ResourceNotFoundException;
import com.almacen.ms_usuarios.mapper.UsuarioMapper;
import com.almacen.ms_usuarios.model.Usuario;
import com.almacen.ms_usuarios.model.enums.RolNombre;
import com.almacen.ms_usuarios.repository.UsuarioRepository;
import com.almacen.ms_usuarios.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario para UsuarioServiceImpl.
 * UsuarioRepository y PasswordEncoder quedan simulados.
 * UsuarioMapper es inyectado como mock (es un bean, no clase estática).
 */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void crearUsuario_cuandoEmailNuevo_deberiaGuardarYRetornarDTO() {

        // ARRANGE
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setNombre("Carlos");
        request.setApellido("Rojas");
        request.setEmail("carlos@correo.cl");
        request.setPassword("clave123");
        request.setRol(RolNombre.ADMIN);
        request.setEstado(true);

        Usuario entidad = Usuario.builder()
                .id(1L)
                .nombre("Carlos")
                .apellido("Rojas")
                .email("carlos@correo.cl")
                .password("$2a$10$hasheada")
                .rol(RolNombre.ADMIN)
                .estado(true)
                .build();

        UsuarioResponseDTO expected = new UsuarioResponseDTO();
        expected.setId(1L);
        expected.setEmail("carlos@correo.cl");
        expected.setRol("ADMIN");

        when(usuarioRepository.existsByEmail("carlos@correo.cl")).thenReturn(false);
        when(passwordEncoder.encode("clave123")).thenReturn("$2a$10$hasheada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(entidad);
        when(usuarioMapper.toDTO(entidad)).thenReturn(expected);

        // ACT
        UsuarioResponseDTO resultado = usuarioService.crearUsuario(request);

        // ASSERT
        assertThat(resultado.getEmail()).isEqualTo("carlos@correo.cl");
        assertThat(resultado.getRol()).isEqualTo("ADMIN");

        verify(passwordEncoder, times(1)).encode("clave123");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void crearUsuario_cuandoEmailDuplicado_deberiaLanzarExcepcion() {

        // ARRANGE
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setEmail("carlos@correo.cl");

        when(usuarioRepository.existsByEmail("carlos@correo.cl")).thenReturn(true);

        // ACT + ASSERT
        assertThatThrownBy(() -> usuarioService.crearUsuario(request))
                .isInstanceOf(DuplicateResourceException.class);

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void listarUsuarios_deberiaRetornarListaDTOs() {

        // ARRANGE
        Usuario u = Usuario.builder().id(1L).email("a@b.cl").build();
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(1L);

        when(usuarioRepository.findAll()).thenReturn(List.of(u));
        when(usuarioMapper.toDTO(u)).thenReturn(dto);

        // ACT
        List<UsuarioResponseDTO> resultado = usuarioService.listarUsuarios();

        // ASSERT
        assertThat(resultado).hasSize(1);
    }

    @Test
    void obtenerUsuarioPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.obtenerUsuarioPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void eliminarUsuario_cuandoExiste_deberiaEliminar() {

        // ARRANGE
        Usuario u = Usuario.builder().id(1L).build();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));
        doNothing().when(usuarioRepository).delete(u);

        // ACT
        usuarioService.eliminarUsuario(1L);

        // VERIFY
        verify(usuarioRepository, times(1)).delete(u);
    }
}
