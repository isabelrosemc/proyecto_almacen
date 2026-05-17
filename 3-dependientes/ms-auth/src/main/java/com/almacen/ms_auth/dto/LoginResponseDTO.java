package com.almacen.ms_auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {

    private String token;

    private String tipo;

    private String email;

    private String rol;
}