package com.almacen.ms_categoria.service;

import com.almacen.ms_categoria.dto.CategoriaRequestDTO;
import com.almacen.ms_categoria.dto.CategoriaResponseDTO;
import com.almacen.ms_categoria.exception.CategoriaNotFoundException;
import com.almacen.ms_categoria.exception.DuplicateCategoriaException;
import com.almacen.ms_categoria.mapper.CategoriaMapper;
import com.almacen.ms_categoria.model.Categoria;
import com.almacen.ms_categoria.repository.CategoriaRepository;
import com.almacen.ms_categoria.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Test unitario para CategoriaServiceImpl.
 *
 * No se levanta Spring Boot, MySQL ni Eureka.
 * Se usa JUnit 5 + Mockito.
 * CategoriaRepository queda simulado.
 */
@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    // ──────────────────────────────────────────────────────────────────
    // crearCategoria
    // ──────────────────────────────────────────────────────────────────

    @Test
    void crearCategoria_cuandoDatosValidos_deberiaRetornarResponseDTO() {

        // ARRANGE: se preparan el request y la entidad simulada que devuelve el repositorio.
        CategoriaRequestDTO request = CategoriaRequestDTO.builder()
                .nombre("Electrónica")
                .descripcion("Productos electrónicos y digitales")
                .estado(true)
                .build();

        Categoria entidadGuardada = new Categoria();
        entidadGuardada.setId(1L);
        entidadGuardada.setNombre("Electrónica");
        entidadGuardada.setDescripcion("Productos electrónicos y digitales");
        entidadGuardada.setEstado(true);
        entidadGuardada.setFechaCreacion(LocalDateTime.now());

        CategoriaResponseDTO responseEsperado = CategoriaResponseDTO.builder()
                .id(1L)
                .nombre("Electrónica")
                .descripcion("Productos electrónicos y digitales")
                .estado(true)
                .build();

        when(categoriaRepository.findByNombreIgnoreCase("Electrónica"))
                .thenReturn(Optional.empty());
        when(categoriaRepository.save(any(Categoria.class)))
                .thenReturn(entidadGuardada);

        try (MockedStatic<CategoriaMapper> mapper = mockStatic(CategoriaMapper.class)) {
            mapper.when(() -> CategoriaMapper.toEntity(request)).thenReturn(entidadGuardada);
            mapper.when(() -> CategoriaMapper.toDTO(entidadGuardada)).thenReturn(responseEsperado);

            // ACT: se ejecuta el método real del service.
            CategoriaResponseDTO resultado = categoriaService.crearCategoria(request);

            // ASSERT: se verifica que el resultado es el esperado.
            assertThat(resultado).isNotNull();
            assertThat(resultado.getNombre()).isEqualTo("Electrónica");
            assertThat(resultado.getId()).isEqualTo(1L);
        }

        // VERIFY: el repositorio fue llamado una vez para guardar.
        verify(categoriaRepository, times(1)).save(any(Categoria.class));

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Si el service no lanza DuplicateCategoriaException cuando ya existe la categoría,
         * el test "crearCategoria_cuandoNombreDuplicado_deberiaLanzarExcepcion" fallará.
         * Desarrollo debe revisar la validación en CategoriaServiceImpl.crearCategoria().
         */
    }

    @Test
    void crearCategoria_cuandoNombreDuplicado_deberiaLanzarExcepcion() {

        // ARRANGE: se simula que ya existe una categoría con ese nombre.
        CategoriaRequestDTO request = CategoriaRequestDTO.builder()
                .nombre("Electrónica")
                .descripcion("Descripción duplicada")
                .estado(true)
                .build();

        when(categoriaRepository.findByNombreIgnoreCase("Electrónica"))
                .thenReturn(Optional.of(new Categoria()));

        // ACT + ASSERT: se espera que se lance la excepción de duplicado.
        assertThatThrownBy(() -> categoriaService.crearCategoria(request))
                .isInstanceOf(DuplicateCategoriaException.class);

        verify(categoriaRepository, never()).save(any());
    }

    // ──────────────────────────────────────────────────────────────────
    // listarCategorias
    // ──────────────────────────────────────────────────────────────────

    @Test
    void listarCategorias_deberiaRetornarListaDeResponseDTOs() {

        // ARRANGE: el repositorio retorna una lista con una entidad.
        Categoria c = new Categoria();
        c.setId(1L);
        c.setNombre("Alimentos");

        CategoriaResponseDTO dto = CategoriaResponseDTO.builder()
                .id(1L).nombre("Alimentos").build();

        when(categoriaRepository.findAll()).thenReturn(List.of(c));

        try (MockedStatic<CategoriaMapper> mapper = mockStatic(CategoriaMapper.class)) {
            mapper.when(() -> CategoriaMapper.toDTO(c)).thenReturn(dto);

            // ACT
            List<CategoriaResponseDTO> resultado = categoriaService.listarCategorias();

            // ASSERT
            assertThat(resultado).hasSize(1);
            assertThat(resultado.get(0).getNombre()).isEqualTo("Alimentos");
        }
    }

    // ──────────────────────────────────────────────────────────────────
    // buscarPorId
    // ──────────────────────────────────────────────────────────────────

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornarResponseDTO() {

        // ARRANGE
        Categoria c = new Categoria();
        c.setId(1L);
        c.setNombre("Ropa");

        CategoriaResponseDTO dto = CategoriaResponseDTO.builder()
                .id(1L).nombre("Ropa").build();

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(c));

        try (MockedStatic<CategoriaMapper> mapper = mockStatic(CategoriaMapper.class)) {
            mapper.when(() -> CategoriaMapper.toDTO(c)).thenReturn(dto);

            // ACT
            CategoriaResponseDTO resultado = categoriaService.buscarPorId(1L);

            // ASSERT
            assertThat(resultado.getId()).isEqualTo(1L);
        }
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        // ARRANGE
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThatThrownBy(() -> categoriaService.buscarPorId(99L))
                .isInstanceOf(CategoriaNotFoundException.class);
    }

    // ──────────────────────────────────────────────────────────────────
    // eliminarCategoria
    // ──────────────────────────────────────────────────────────────────

    @Test
    void eliminarCategoria_cuandoExiste_deberiaEliminar() {

        // ARRANGE
        Categoria c = new Categoria();
        c.setId(1L);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(c));
        doNothing().when(categoriaRepository).delete(c);

        // ACT
        categoriaService.eliminarCategoria(1L);

        // VERIFY
        verify(categoriaRepository, times(1)).delete(c);
    }
}
