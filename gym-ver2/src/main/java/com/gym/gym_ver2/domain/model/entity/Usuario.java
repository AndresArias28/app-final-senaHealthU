package com.gym.gym_ver2.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email_usuario"),
        @UniqueConstraint(columnNames = "contrasena_usuario")
})
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol", nullable = false)
   // @JsonBackReference
    private Rol idRol;

    @Column(name="nombre_usuario")
    private String nombreUsuario;

    @Column(name="apellido_usuario")
    private String apellidoUsuario;

    @Column(name="cedula_usuario")
    private String cedulaUsuario;

    @Column(name="fecha_nacimineto")
    private Date fechaNacimiento;

    @Column(name = "contrasena_usuario")
    private String contrasenaUsuario;

    @Column(name = "email_usuario")
    private String emailUsuario;

    @Column(name = "estatura_usuario")
    private Double estaturaUsuario;

    @Column(name = "peso_usuario")
    private Double pesoUsuario;

    @Column(name = "puntos_acumulados_usuario")
    private Integer puntosUsuario;

    @Column(name = "numero_ficha_usuario")
    private Integer numeroFicha;

    @Column(name = "recompensa_horas_usuario")
    private Integer horasRecompensas;

    @Column(name = "nivel_actual_usuario")
    private Integer nivelActualUsuario;

    @Column(name = "codigoqr_admin")
    private String qrAdmin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((idRol.getNombreRol())));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
