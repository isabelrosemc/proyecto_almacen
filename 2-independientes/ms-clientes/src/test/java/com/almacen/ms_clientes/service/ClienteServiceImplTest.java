package com.almacen.ms_clientes.service;

import com.almacen.ms_clientes.dto.ClienteRequestDTO;
import com.almacen.ms_clientes.dto.ClienteResponseDTO;
import com.almacen.ms_clientes.exception.ClienteNotFoundException;
import com.almacen.ms_clientes.exception.DuplicateClienteException;
import com.almacen.ms_clientes.mapper.ClienteMapper;
import com.almacen.ms_clientes.model.Cliente;
import com.almacen.ms_clientes.repository.ClienteRepository;
import com.almacen.ms_clientes.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario para ClienteServiceImpl.
 * ClienteRepository queda simulado con Mockito.
 */
@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    // ──────────────────────────────────────────────────────────────────
    // crear
    // ──────────────────────────────────────────────────────────────────

    @Test
    void crear_cuandoEmailNuevo_deberiaGuardarYRetornarDTO() {

        // ARRANGE
        ClienteRequestDTO request = new ClienteRequestDTO();
        request.setNombre("Ana");
        request.setApellido("García");
        request.setEmail("ana@correo.cl");
        request.setTelefono("912345678");
        request.setDireccion("Av. Principal 10");

        Cliente entidad = new Cliente();
        entidad.setId(1L);
        entidad.setNombre("Ana");
        entidad.setApellido("García");
        entidad.setEmail("ana@correo.cl");
        entidad.setEstado(true);
        entidad.setFechaRegistro(LocalDateTime.now());

        ClienteResponseDTO expected = ClienteResponseDTO.builder()
                .id(1L)
                .nombreCompleto("Ana García")
                .email("ana@correo.cl")
                .estado(true)
                .build();

        when(repository.existsByEmail("ana@correo.cl")).thenReturn(false);
        when(repository.save(any(Cliente.class))).thenReturn(entidad);

        try (MockedStatic<ClienteMapper> mapper = mockStatic(ClienteMapper.class)) {
            mapper.when(() -> ClienteMapper.toEntity(request)).thenReturn(entidad);
            mapper.when(() -> ClienteMapper.toDTO(entidad)).thenReturn(expected);

            // ACT
            ClienteResponseDTO resultado = clienteService.crear(request);

            // ASSERT
            assertThat(resultado.getEmail()).isEqualTo("ana@correo.cl");
            assertThat(resultado.getId()).isEqualTo(1L);
        }

        verify(repository, times(1)).save(any(Cliente.class));
    }

    @Test
    void crear_cuandoEmailDuplicado_deberiaLanzarExcepcion() {

        // ARRANGE
        ClienteRequestDTO request = new ClienteRequestDTO();
        request.setEmail("ana@correo.cl");

        when(repository.existsByEmail("ana@correo.cl")).thenReturn(true);

        // ACT + ASSERT
        assertThatThrownBy(() -> clienteService.crear(request))
                .isInstanceOf(DuplicateClienteException.class);

        verify(repository, never()).save(any());
    }

    // ──────────────────────────────────────────────────────────────────
    // listar
    // ──────────────────────────────────────────────────────────────────

    @Test
    void listar_deberiaRetornarSoloClientesActivos() {

        // ARRANGE: un cliente activo y uno inactivo.
        Cliente activo = new Cliente();
        activo.setId(1L);
        activo.setEstado(true);

        Cliente inactivo = new Cliente();
        inactivo.setId(2L);
        inactivo.setEstado(false);

        ClienteResponseDTO dto = ClienteResponseDTO.builder().id(1L).estado(true).build();

        when(repository.findAll()).thenReturn(List.of(activo, inactivo));

        try (MockedStatic<ClienteMapper> mapper = mockStatic(ClienteMapper.class)) {
            mapper.when(() -> ClienteMapper.toDTO(activo)).thenReturn(dto);

            // ACT
            List<ClienteResponseDTO> resultado = clienteService.listar();

            // ASSERT: solo el activo debe aparecer.
            assertThat(resultado).hasSize(1);
            assertThat(resultado.get(0).getId()).isEqualTo(1L);
        }
    }

    // ──────────────────────────────────────────────────────────────────
    // buscarPorId
    // ──────────────────────────────────────────────────────────────────

    @Test
    void buscarPorId_cuandoClienteExisteYActivo_deberiaRetornarDTO() {

        // ARRANGE
        Cliente c = new Cliente();
        c.setId(1L);
        c.setEstado(true);

        ClienteResponseDTO dto = ClienteResponseDTO.builder().id(1L).estado(true).build();

        when(repository.findById(1L)).thenReturn(Optional.of(c));

        try (MockedStatic<ClienteMapper> mapper = mockStatic(ClienteMapper.class)) {
            mapper.when(() -> ClienteMapper.toDTO(c)).thenReturn(dto);

            // ACT
            ClienteResponseDTO resultado = clienteService.buscarPorId(1L);

            // ASSERT
            assertThat(resultado.getId()).isEqualTo(1L);
        }
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.buscarPorId(99L))
                .isInstanceOf(ClienteNotFoundException.class);
    }

    @Test
    void buscarPorId_cuandoExistePeroInactivo_deberiaLanzarExcepcion() {

        // ARRANGE: cliente existe pero está inactivo.
        Cliente inactivo = new Cliente();
        inactivo.setId(1L);
        inactivo.setEstado(false);

        when(repository.findById(1L)).thenReturn(Optional.of(inactivo));

        // ACT + ASSERT
        assertThatThrownBy(() -> clienteService.buscarPorId(1L))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessageContaining("inactivo");
    }

    // ──────────────────────────────────────────────────────────────────
    // eliminar (soft delete)
    // ──────────────────────────────────────────────────────────────────

    @Test
    void eliminar_cuandoExiste_deberiaDesactivarCliente() {

        // ARRANGE
        Cliente c = new Cliente();
        c.setId(1L);
        c.setEstado(true);

        when(repository.findById(1L)).thenReturn(Optional.of(c));
        when(repository.save(c)).thenReturn(c);

        // ACT
        clienteService.eliminar(1L);

        // ASSERT: después del soft delete el estado debe ser false.
        assertThat(c.getEstado()).isFalse();
        verify(repository, times(1)).save(c);
    }
}
