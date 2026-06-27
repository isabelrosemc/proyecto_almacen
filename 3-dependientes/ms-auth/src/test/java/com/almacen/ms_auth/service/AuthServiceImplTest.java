package com.almacen.ms_auth.service;

import com.almacen.ms_auth.client.UsuarioClient;
import com.almacen.ms_auth.dto.LoginRequestDTO;
import com.almacen.ms_auth.dto.LoginResponseDTO;
import com.almacen.ms_auth.dto.UsuarioResponseDTO;
import com.almacen.ms_auth.exception.UnauthorizedException;
import com.almacen.ms_auth.service.impl.AuthServiceImpl;
import com.almacen.ms_auth.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario para AuthServiceImpl.
 * UsuarioClient, JwtUtil y PasswordEncoder quedan simulados.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_cuandoCredencialesCorrectas_deberiaRetornarTokenJWT() {

        // ARRANGE
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("admin@correo.cl");
        request.setPassword("clave123");

        UsuarioResponseDTO usuario = new UsuarioResponseDTO();
        usuario.setEmail("admin@correo.cl");
        usuario.setPassword("$2a$10$hasheada");
        usuario.setEstado(true);
        usuario.setRol("ADMIN");

        when(usuarioClient.obtenerUsuarioPorEmail("admin@correo.cl")).thenReturn(usuario);
        when(passwordEncoder.matches("clave123", "$2a$10$hasheada")).thenReturn(true);
        when(jwtUtil.generarToken("admin@correo.cl", "ADMIN")).thenReturn("jwt-token-simulado");

        // ACT
        LoginResponseDTO resultado = authService.login(request);

        // ASSERT
        assertThat(resultado.getToken()).isEqualTo("jwt-token-simulado");
        assertThat(resultado.getEmail()).isEqualTo("admin@correo.cl");
        assertThat(resultado.getTipo()).isEqualTo("Bearer");

        verify(jwtUtil, times(1)).generarToken("admin@correo.cl", "ADMIN");

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Si el service no lanza UnauthorizedException cuando la password no coincide,
         * el test "login_cuandoPasswordIncorrecta_deberiaLanzarExcepcion" fallará.
         */
    }

    @Test
    void login_cuandoPasswordIncorrecta_deberiaLanzarExcepcion() {

        // ARRANGE
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("admin@correo.cl");
        request.setPassword("claveErronea");

        UsuarioResponseDTO usuario = new UsuarioResponseDTO();
        usuario.setEmail("admin@correo.cl");
        usuario.setPassword("$2a$10$hasheada");
        usuario.setEstado(true);

        when(usuarioClient.obtenerUsuarioPorEmail("admin@correo.cl")).thenReturn(usuario);
        when(passwordEncoder.matches("claveErronea", "$2a$10$hasheada")).thenReturn(false);

        // ACT + ASSERT
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(UnauthorizedException.class);

        verify(jwtUtil, never()).generarToken(any(), any());
    }

    @Test
    void login_cuandoUsuarioNoEncontrado_deberiaLanzarExcepcion() {

        // ARRANGE
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("noexiste@correo.cl");
        request.setPassword("clave");

        when(usuarioClient.obtenerUsuarioPorEmail("noexiste@correo.cl"))
                .thenThrow(new RuntimeException("Usuario no encontrado"));

        // ACT + ASSERT
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(UnauthorizedException.class);
    }

    @Test
    void login_cuandoUsuarioInactivo_deberiaLanzarExcepcion() {

        // ARRANGE
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("inactivo@correo.cl");
        request.setPassword("clave123");

        UsuarioResponseDTO usuario = new UsuarioResponseDTO();
        usuario.setEmail("inactivo@correo.cl");
        usuario.setPassword("$2a$10$hasheada");
        usuario.setEstado(false);

        when(usuarioClient.obtenerUsuarioPorEmail("inactivo@correo.cl")).thenReturn(usuario);
        when(passwordEncoder.matches("clave123", "$2a$10$hasheada")).thenReturn(true);

        // ACT + ASSERT
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("inactivo");
    }
}
