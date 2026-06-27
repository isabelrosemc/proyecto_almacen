package com.almacen.ms_usuarios.controller;

import com.almacen.ms_usuarios.config.SecurityConfig;
import com.almacen.ms_usuarios.config.PasswordConfig;
import com.almacen.ms_usuarios.dto.UsuarioRequestDTO;
import com.almacen.ms_usuarios.dto.UsuarioResponseDTO;
import com.almacen.ms_usuarios.model.enums.RolNombre;
import com.almacen.ms_usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test unitario para UsuarioController.
 *
 * Se importa SecurityConfig y PasswordConfig porque ms-usuarios usa
 * Spring Security. SecurityConfig ya tiene permitAll() para /api/usuarios/**,
 * así que los tests no necesitan autenticación.
 */
@WebMvcTest(UsuarioController.class)
@Import({ SecurityConfig.class, PasswordConfig.class })
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    void listarUsuarios_deberiaRetornar200YLista() throws Exception {

        // ARRANGE
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(1L);
        dto.setEmail("carlos@correo.cl");
        dto.setRol("ADMIN");

        when(usuarioService.listarUsuarios()).thenReturn(List.of(dto));

        // ACT + ASSERT
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value("carlos@correo.cl"));

        verify(usuarioService, times(1)).listarUsuarios();
    }

    @Test
    void obtenerUsuarioPorId_cuandoExiste_deberiaRetornar200() throws Exception {

        // ARRANGE
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(1L);
        dto.setEmail("carlos@correo.cl");

        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(dto);

        // ACT + ASSERT
        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(usuarioService, times(1)).obtenerUsuarioPorId(1L);
    }

    @Test
    void crearUsuario_conDatosValidos_deberiaRetornar201() throws Exception {

        // ARRANGE
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setNombre("Laura");
        request.setApellido("Pérez");
        request.setEmail("laura@correo.cl");
        request.setPassword("clave456");
        request.setRol(RolNombre.OPERADOR);
        request.setEstado(true);

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(2L);
        response.setEmail("laura@correo.cl");

        when(usuarioService.crearUsuario(any(UsuarioRequestDTO.class))).thenReturn(response);

        // ACT + ASSERT
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L));

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Se esperaba 201 Created, se obtuvo 200 OK.
         */
    }

    @Test
    void eliminarUsuario_cuandoExiste_deberiaRetornar204() throws Exception {

        doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).eliminarUsuario(1L);
    }
}
