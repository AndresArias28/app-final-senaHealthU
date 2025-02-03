package com.gym.gym_ver2.domain.model.requestModels;

import com.gym.gym_ver2.domain.model.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAdminRequest {
    String nombreAdmin;
    String apellidoAdmin;
    String cedulaAdmin;
    String contrasenaAdmin;
    String emailAdmin;
}
