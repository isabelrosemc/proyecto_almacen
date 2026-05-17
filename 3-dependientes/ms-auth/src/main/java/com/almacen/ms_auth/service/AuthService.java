package com.almacen.ms_auth.service;

import com.almacen.ms_auth.dto.AuthResponseDTO;
import com.almacen.ms_auth.dto.LoginRequestDTO;

public interface AuthService {

    AuthResponseDTO login(LoginRequestDTO dto);
}