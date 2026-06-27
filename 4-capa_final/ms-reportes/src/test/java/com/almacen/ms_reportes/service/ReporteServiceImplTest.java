package com.almacen.ms_reportes.service;

import com.almacen.ms_reportes.client.VentasClient;
import com.almacen.ms_reportes.dto.VentaResponseDTO;
import com.almacen.ms_reportes.service.Impl.ReporteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario para ReporteServiceImpl.
 * VentasClient (Feign) queda simulado con Mockito.
 * No se levanta Spring Boot, MySQL ni Eureka.
 */
@ExtendWith(MockitoExtension.class)
class ReporteServiceImplTest {

    @Mock
    private VentasClient ventasClient;

    @InjectMocks
    private ReporteServiceImpl reporteService;

    @Test
    void obtenerReporteVentas_cuandoHayVentas_deberiaRetornarLista() {

        // ARRANGE: el Feign client simulado devuelve una lista de ventas.
        VentaResponseDTO venta1 = VentaResponseDTO.builder()
                .id(1L)
                .total(new BigDecimal("29990"))
                .estado(true)
                .build();

        VentaResponseDTO venta2 = VentaResponseDTO.builder()
                .id(2L)
                .total(new BigDecimal("59980"))
                .estado(true)
                .build();

        when(ventasClient.listarVentas()).thenReturn(List.of(venta1, venta2));

        // ACT: se ejecuta el método real del service.
        List<VentaResponseDTO> resultado = reporteService.obtenerReporteVentas();

        // ASSERT: el reporte debe retornar las dos ventas simuladas.
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        assertThat(resultado.get(1).getTotal()).isEqualByComparingTo("59980");

        // VERIFY: el Feign client fue invocado exactamente una vez.
        verify(ventasClient, times(1)).listarVentas();

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Si VentasClient lanza una excepción (ej. ms-ventas caído),
         * el service no tiene manejo de error definido y la excepción
         * subirá al controller, resultando en un 500 Internal Server Error.
         * Desarrollo debería agregar un bloque try/catch o un fallback en el FeignClient.
         */
    }

    @Test
    void obtenerReporteVentas_cuandoNoHayVentas_deberiaRetornarListaVacia() {

        // ARRANGE: el Feign client devuelve lista vacía.
        when(ventasClient.listarVentas()).thenReturn(List.of());

        // ACT
        List<VentaResponseDTO> resultado = reporteService.obtenerReporteVentas();

        // ASSERT
        assertThat(resultado).isEmpty();

        verify(ventasClient, times(1)).listarVentas();
    }
}
