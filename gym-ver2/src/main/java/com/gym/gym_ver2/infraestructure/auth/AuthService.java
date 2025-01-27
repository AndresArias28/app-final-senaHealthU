package com.gym.gym_ver2.infraestructure.auth;

import com.gym.gym_ver2.domain.model.entity.Rol;
import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.infraestructure.jwt.JwtService;
import com.gym.gym_ver2.infraestructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest rq) {

        // Validar que el email y la contraseña no estén vacíos
        if (rq.getContrasenaUsuario() == null || rq.getContrasenaUsuario().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        // // Validar que el usuario y la contraseña sean correctos
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(rq.getEmailUsuario(), rq.getContrasenaUsuario() )
        );
        // recuperar usuario  segun la informacion del request
        Usuario usuario = userRepository.findByEmailUsuario(rq.getEmailUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        //crear token con el usuario
        String token = jwtService.createToken(new HashMap<>(),
                new org.springframework.security.core.userdetails.User(
                        usuario.getEmailUsuario(),
                        usuario.getContrasenaUsuario(),
                        new ArrayList<>()
                )
        );
        //crear la respuesta con el token
        return AuthResponse.builder().token(token).build();

    }

    public AuthResponse register(RegisterRequest rq) {
        // mediante el patron builder se crea un usuario con la informacion del request
        Usuario usuario = Usuario.builder()
                .nombreUsuario(rq.getNombreUsuario())
                .apellidoUsuario(rq.getApellidoUsuario())
                .emailUsuario(rq.getEmailUsuario())
                .cedulaUsuario(rq.getCedulaUsuario())
                .estaturaUsuario(rq.getEstaturaUsuario())
                .pesoUsuario(rq.getPesoUsuario())
                .nivelActualUsuario(rq.getNivelActualUsuario())
                .fechaNacimiento( rq.getFechaNacimiento())
                .horasRecompensas(rq.getHorasRecompensas())
                .numeroFicha(rq.getNumeroFicha())
                .contrasenaUsuario( passwordEncoder.encode(rq.getContrasenaUsuario()))
                .idRol(Rol.builder().idRol(2).build())
                .build();
        //guardar el usuario en la base de datos
        userRepository.save(usuario);
        //crear token con el usuario creado y retornar la respuesta
        return AuthResponse.builder()
                .token(jwtService.createToken(usuario))
                .build();
    }

}
