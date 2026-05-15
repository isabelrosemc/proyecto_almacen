package com.example.ms_pagos.service;

import com.example.ms_pagos.dto.PagoDTO;

import java.util.List;

public interface PagoService {

    List<PagoDTO> listarPagos();

    PagoDTO buscarPago(Long id);

    PagoDTO guardarPago(PagoDTO dto);

    PagoDTO actualizarPago(Long id, PagoDTO dto);

    void eliminarPago(Long id);
}