package com.gym.gym_ver2.domain.model.pojos;

import com.gym.gym_ver2.domain.model.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegisterRequest {
    private String nombreUsuario;
    private String apellidoUsuario;
    private String cedulaUsuario;
    private Date fechaNacimiento;
    private String contrasenaUsuario;
    private String emailUsuario;

}
