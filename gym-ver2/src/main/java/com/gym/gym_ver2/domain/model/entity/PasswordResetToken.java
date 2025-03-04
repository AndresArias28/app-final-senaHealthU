package com.gym.gym_ver2.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "passwordResetToken")
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)                                                                                                                                                 
    @Column(name = "id_token")
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id_usuario", name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_expiracion")
    private LocalDateTime expiryDate;

//    public PasswordResetToken(String token, Usuario usuario, LocalDateTime localDateTime) {
//        this.token = token;
//        this.usuario = usuario;
//        this.expiryDate = expiryDate;
//    }
}
