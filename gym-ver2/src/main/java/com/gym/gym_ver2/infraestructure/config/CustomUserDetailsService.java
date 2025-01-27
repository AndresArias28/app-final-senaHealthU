package com.gym.gym_ver2.infraestructure.config;

import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.infraestructure.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

// Clase que implementa la interfaz UserDetailsService de Spring Security
// para cargar un usuario por su email y devolver un objeto UserDetails
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository userRepository;

    public CustomUserDetailsService(UsuarioRepository userRepository1) {
        this.userRepository = userRepository1;
    }

    // Metodo que carga un usuario por su email y devuelve un objeto UserDetails
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByEmailUsuario(email)
                .orElseThrow(() -> {
                    System.out.println("Usuario no encontrado: " + email);
                    return new UsernameNotFoundException("Usuario no encontrado con email: " + email);
                });
        System.out.println("Usuario encontrado: " + usuario.getEmailUsuario());
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmailUsuario(),
                usuario.getContrasenaUsuario(), // Contraseña encriptada de la base de datos
                new ArrayList<>()
        );
    }
}
