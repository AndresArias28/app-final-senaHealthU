package com.gym.gym_ver2.domain.model.pojos;

import com.gym.gym_ver2.domain.model.entity.Rol;
import jakarta.persistence.Column;
import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UsuarioDTO {

     private Integer idUsuario;
     private String nombreUsuario;
     private String emailUsuario;
     private Integer rol;

}

