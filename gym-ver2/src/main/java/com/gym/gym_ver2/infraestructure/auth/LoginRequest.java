package com.gym.gym_ver2.infraestructure.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String emailUsuario;
    private String contrasenaUsuario;

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }
}


