package com.almacen.ms_auth.controller;

import com.almacen.ms_auth.config.PasswordConfig;
import com.almacen.ms_auth.dto.LoginRequestDTO;
import com.almacen.ms_auth.dto.LoginResponseDTO;
import com.almacen.ms_auth.security.JwtAuthenticationFilter;
import com.almacen.ms_auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // 👈 CLAVE: desactiva Spring Security en tests
@Import({PasswordConfig.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void login_conCredencialesValidas_deberiaRetornar200YToken() throws Exception {

        // ARRANGE
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("admin@correo.cl");
        request.setPassword("clave123");

        LoginResponseDTO response = LoginResponseDTO.builder()
                .token("jwt-token-simulado")
                .tipo("Bearer")
                .email("admin@correo.cl")
                .rol("ADMIN")
                .build();

        when(authService.login(any(LoginRequestDTO.class))).thenReturn(response);

        // ACT + ASSERT
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token-simulado"))
                .andExpect(jsonPath("$.tipo").value("Bearer"))
                .andExpect(jsonPath("$.email").value("admin@correo.cl"))
                .andExpect(jsonPath("$.rol").value("ADMIN"));

        verify(authService, times(1)).login(any(LoginRequestDTO.class));
    }
}