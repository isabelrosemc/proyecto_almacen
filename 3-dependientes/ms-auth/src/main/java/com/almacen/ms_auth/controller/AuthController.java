package com.almacen.ms_auth.controller;

import com.almacen.ms_auth.dto.AuthResponseDTO;
import com.almacen.ms_auth.dto.LoginRequestDTO;
import com.almacen.ms_auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        log.info("POST /api/auth/login");

        return ResponseEntity.ok(service.login(dto));
    }
}