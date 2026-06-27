package com.almacen.ms_proveedores.service;

import com.almacen.ms_proveedores.dto.ProveedorRequestDTO;
import com.almacen.ms_proveedores.dto.ProveedorResponseDTO;
import com.almacen.ms_proveedores.exception.DuplicateProveedorException;
import com.almacen.ms_proveedores.exception.ProveedorNotFoundException;
import com.almacen.ms_proveedores.mapper.ProveedorMapper;
import com.almacen.ms_proveedores.model.Proveedor;
import com.almacen.ms_proveedores.repository.ProveedorRepository;
import com.almacen.ms_proveedores.service.impl.ProveedorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario para ProveedorServiceImpl.
 * ProveedorRepository queda simulado con Mockito.
 */
@ExtendWith(MockitoExtension.class)
class ProveedorServiceImplTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorServiceImpl proveedorService;

    @Test
    void crearProveedor_cuandoDatosNuevos_deberiaGuardarYRetornarDTO() {

        // ARRANGE
        ProveedorRequestDTO request = ProveedorRequestDTO.builder()
                .razonSocial("Distribuidora XYZ Ltda.")
                .rut("76543210-9")
                .email("contacto@xyz.cl")
                .telefono("22345678")
                .direccion("Calle Comercio 45")
                .estado(true)
                .build();

        Proveedor entidad = new Proveedor();
        entidad.setId(1L);
        entidad.setRazonSocial("Distribuidora XYZ Ltda.");
        entidad.setRut("76543210-9");
        entidad.setEmail("contacto@xyz.cl");

        ProveedorResponseDTO expected = ProveedorResponseDTO.builder()
                .id(1L)
                .razonSocial("Distribuidora XYZ Ltda.")
                .rut("76543210-9")
                .email("contacto@xyz.cl")
                .build();

        when(proveedorRepository.existsByRut("76543210-9")).thenReturn(false);
        when(proveedorRepository.existsByEmail("contacto@xyz.cl")).thenReturn(false);
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(entidad);

        try (MockedStatic<ProveedorMapper> mapper = mockStatic(ProveedorMapper.class)) {
            mapper.when(() -> ProveedorMapper.toEntity(request)).thenReturn(entidad);
            mapper.when(() -> ProveedorMapper.toDTO(entidad)).thenReturn(expected);

            // ACT
            ProveedorResponseDTO resultado = proveedorService.crearProveedor(request);

            // ASSERT
            assertThat(resultado.getRut()).isEqualTo("76543210-9");
        }

        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    void crearProveedor_cuandoRutDuplicado_deberiaLanzarExcepcion() {

        // ARRANGE
        ProveedorRequestDTO request = ProveedorRequestDTO.builder()
                .rut("76543210-9")
                .email("nuevo@email.cl")
                .build();

        when(proveedorRepository.existsByRut("76543210-9")).thenReturn(true);

        // ACT + ASSERT
        assertThatThrownBy(() -> proveedorService.crearProveedor(request))
                .isInstanceOf(DuplicateProveedorException.class);

        verify(proveedorRepository, never()).save(any());
    }

    @Test
    void crearProveedor_cuandoEmailDuplicado_deberiaLanzarExcepcion() {

        // ARRANGE
        ProveedorRequestDTO request = ProveedorRequestDTO.builder()
                .rut("99999999-9")
                .email("contacto@xyz.cl")
                .build();

        when(proveedorRepository.existsByRut("99999999-9")).thenReturn(false);
        when(proveedorRepository.existsByEmail("contacto@xyz.cl")).thenReturn(true);

        // ACT + ASSERT
        assertThatThrownBy(() -> proveedorService.crearProveedor(request))
                .isInstanceOf(DuplicateProveedorException.class);

        verify(proveedorRepository, never()).save(any());
    }

    @Test
    void listarProveedores_deberiaRetornarListaDTOs() {

        // ARRANGE
        Proveedor p = new Proveedor();
        p.setId(1L);
        ProveedorResponseDTO dto = ProveedorResponseDTO.builder().id(1L).build();

        when(proveedorRepository.findAll()).thenReturn(List.of(p));

        try (MockedStatic<ProveedorMapper> mapper = mockStatic(ProveedorMapper.class)) {
            mapper.when(() -> ProveedorMapper.toDTO(p)).thenReturn(dto);

            // ACT
            List<ProveedorResponseDTO> resultado = proveedorService.listarProveedores();

            // ASSERT
            assertThat(resultado).hasSize(1);
        }
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        when(proveedorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> proveedorService.buscarPorId(99L))
                .isInstanceOf(ProveedorNotFoundException.class);
    }
}
