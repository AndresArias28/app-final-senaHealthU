package com.gym.gym_ver2.domain.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AdminDTO {
    private Integer idAdmin;
    private String nombreAdmin;
    private String emailAdmin;
    private Integer rol;
}
