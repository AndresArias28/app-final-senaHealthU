package com.gym.gym_ver2.domain.model.dto;

import lombok.*;

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

