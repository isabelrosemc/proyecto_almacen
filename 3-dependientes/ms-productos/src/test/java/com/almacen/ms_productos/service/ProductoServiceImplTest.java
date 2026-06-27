package com.almacen.ms_productos.service;

import com.almacen.ms_productos.client.CategoriaFeignClient;
import com.almacen.ms_productos.client.ProveedorFeignClient;
import com.almacen.ms_productos.dto.*;
import com.almacen.ms_productos.exception.DuplicateProductoException;
import com.almacen.ms_productos.exception.ProductoNotFoundException;
import com.almacen.ms_productos.mapper.ProductoMapper;
import com.almacen.ms_productos.model.Producto;
import com.almacen.ms_productos.repository.ProductoRepository;
import com.almacen.ms_productos.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario para ProductoServiceImpl.
 * ProductoRepository, CategoriaFeignClient y ProveedorFeignClient quedan simulados.
 */
@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaFeignClient categoriaFeignClient;

    @Mock
    private ProveedorFeignClient proveedorFeignClient;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void crearProducto_cuandoSkuNuevo_deberiaGuardarYRetornarDTO() {

        // ARRANGE
        ProductoRequestDTO request = ProductoRequestDTO.builder()
                .nombre("Laptop HP")
                .descripcion("Laptop 15 pulgadas")
                .sku("SKU-001")
                .precio(new BigDecimal("599990"))
                .categoriaId(1L)
                .proveedorId(2L)
                .estado(true)
                .build();

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        ProveedorDTO proveedorDTO = new ProveedorDTO();

        Producto entidad = new Producto();
        entidad.setId(1L);
        entidad.setSku("SKU-001");

        ProductoResponseDTO expected = ProductoResponseDTO.builder()
                .id(1L)
                .sku("SKU-001")
                .nombre("Laptop HP")
                .build();

        when(productoRepository.existsBySku("SKU-001")).thenReturn(false);
        when(categoriaFeignClient.obtenerCategoria(1L)).thenReturn(categoriaDTO);
        when(proveedorFeignClient.obtenerProveedor(2L)).thenReturn(proveedorDTO);
        when(productoRepository.save(any(Producto.class))).thenReturn(entidad);

        try (MockedStatic<ProductoMapper> mapper = mockStatic(ProductoMapper.class)) {
            mapper.when(() -> ProductoMapper.toEntity(request)).thenReturn(entidad);
            mapper.when(() -> ProductoMapper.toDTO(entidad, categoriaDTO, proveedorDTO))
                    .thenReturn(expected);

            // ACT
            ProductoResponseDTO resultado = productoService.crearProducto(request);

            // ASSERT
            assertThat(resultado.getSku()).isEqualTo("SKU-001");
        }

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void crearProducto_cuandoSkuDuplicado_deberiaLanzarExcepcion() {

        // ARRANGE
        ProductoRequestDTO request = ProductoRequestDTO.builder()
                .sku("SKU-001")
                .build();

        when(productoRepository.existsBySku("SKU-001")).thenReturn(true);

        // ACT + ASSERT
        assertThatThrownBy(() -> productoService.crearProducto(request))
                .isInstanceOf(DuplicateProductoException.class);

        verify(productoRepository, never()).save(any());
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoService.buscarPorId(99L))
                .isInstanceOf(ProductoNotFoundException.class);
    }

    @Test
    void listarProductos_deberiaRetornarListaDTOs() {

        // ARRANGE
        Producto p = new Producto();
        p.setId(1L);
        p.setCategoriaId(1L);
        p.setProveedorId(2L);

        CategoriaDTO catDTO = new CategoriaDTO();
        ProveedorDTO provDTO = new ProveedorDTO();
        ProductoResponseDTO dto = ProductoResponseDTO.builder().id(1L).build();

        when(productoRepository.findAll()).thenReturn(List.of(p));
        when(categoriaFeignClient.obtenerCategoria(1L)).thenReturn(catDTO);
        when(proveedorFeignClient.obtenerProveedor(2L)).thenReturn(provDTO);

        try (MockedStatic<ProductoMapper> mapper = mockStatic(ProductoMapper.class)) {
            mapper.when(() -> ProductoMapper.toDTO(p, catDTO, provDTO)).thenReturn(dto);

            List<ProductoResponseDTO> resultado = productoService.listarProductos();

            assertThat(resultado).hasSize(1);
        }
    }

    @Test
    void eliminarProducto_cuandoNoExiste_deberiaLanzarExcepcion() {

        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoService.eliminarProducto(99L))
                .isInstanceOf(ProductoNotFoundException.class);
    }
}
