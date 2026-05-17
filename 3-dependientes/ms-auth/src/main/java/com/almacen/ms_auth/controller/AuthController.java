package com.almacen.ms_auth.controller;

import com.almacen.ms_auth.dto.LoginRequestDTO;
import com.almacen.ms_auth.dto.LoginResponseDTO;
import com.almacen.ms_auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(

            @Valid
            @RequestBody
            LoginRequestDTO request

    ) {

        log.info("Solicitud de login recibida");

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}