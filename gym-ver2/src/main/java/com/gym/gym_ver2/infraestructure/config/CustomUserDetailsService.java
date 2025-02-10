package com.gym.gym_ver2.infraestructure.config;

import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.infraestructure.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

// Clase que implementa la interfaz UserDetailsService de Spring Security, para cargar un usuario por su email y devolver un objeto UserDetails
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository userRepository;

    public CustomUserDetailsService(UsuarioRepository userRepository1) {
        this.userRepository = userRepository1;
    }

    @Override // Metodo que carga un usuario por su email y devuelve un objeto UserDetails
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByEmailUsuario(email)
                .orElseThrow(() ->   new UsernameNotFoundException("Usuario no encontrado con email: " + email));
        System.out.println("Usuario encontrado: " + usuario.getEmailUsuario());


        GrantedAuthority authority = new SimpleGrantedAuthority( usuario.getIdRol().getNombreRol());
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+usuario.getIdRol().getNombreRol()));

        System.out.println("Autoridades cargadas: " + authorities);
        System.out.println("Cargando usuario: " + email);


        return new org.springframework.security.core.userdetails.User(// Devuelve un objeto UserDetails
                usuario.getEmailUsuario(),
                usuario.getContrasenaUsuario(), // Contraseña encriptada de la base de datos
                authorities // Lista de roles del usuario
        );
    }
}
