package com.almacen.ms_auth.service;

import com.almacen.ms_auth.dto.LoginRequestDTO;
import com.almacen.ms_auth.dto.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(
            LoginRequestDTO request
    );
}