package com.gym.gym_ver2.infraestructure.auth;

import lombok.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String nombreUsuario;
    String apellidoUsuario;
    String cedulaUsuario;
    Date fechaNacimiento;
    String contrasenaUsuario;
    String emailUsuario;
    Double estaturaUsuario;
    Double pesoUsuario;
    Integer puntosUsuarios;
    Integer numeroFicha;
    Integer horasRecompensas;
    Integer nivelActualUsuario;
}
