package com.gym.gym_ver2.infraestructure.auth;

import com.gym.gym_ver2.domain.model.entity.Rol;
import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.domain.model.pojos.AdminRegisterRequest;
import com.gym.gym_ver2.infraestructure.jwt.JwtService;
import com.gym.gym_ver2.infraestructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
        if (rq.getEmailUsuario() == null || rq.getEmailUsuario().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (rq.getContrasenaUsuario() == null || rq.getContrasenaUsuario().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        try{
            authenticationManager.authenticate(// autentica que el usuario y la contraseña sean correctos
                    new UsernamePasswordAuthenticationToken(rq.getEmailUsuario(), rq.getContrasenaUsuario() )
            );
            UserDetails userDetails = userRepository.findByEmailUsuario(rq.getEmailUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                   // (UserDetails) authentication.getPrincipal(); // Recuperar el objeto UserDetails del usuario autenticado
            //crear token con el usuario
            HashMap<String, Object> aux = new HashMap<>();
            aux.put("sub", rq.getEmailUsuario());
            String token = jwtService.generateToken(aux, userDetails);
            //crear la respuesta con el token
            return AuthResponse.builder().token(token).build();

    }catch (Exception e) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
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
                .contrasenaUsuario( passwordEncoder.encode(rq.getContrasenaUsuario()) )
                .idRol(Rol.builder().idRol(3).build())//po defecto se asigna el rol de usuario
                .build();

        userRepository.save(usuario);//guardar el usuario en la base de datos
        //crear token con el usuario creado y retornar la respuesta
        return AuthResponse.builder().token(jwtService.createToken(usuario)).build();
    }

    public Usuario getUsuarioActual( String email) {
        return userRepository.findByEmailUsuario(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

//    @PreAuthorize("hasRole('ROLE_Superusuario')")
//    public String registerAdmin(AdminRegisterRequest rq) {
//        Usuario usuario = Usuario.builder()
//                .nombreUsuario(rq.getNombreUsuario())
//                .apellidoUsuario(rq.getApellidoUsuario())
//                .emailUsuario(rq.getEmailUsuario())
//                .cedulaUsuario(rq.getCedulaUsuario())
//                .contrasenaUsuario( passwordEncoder.encode(rq.getContrasenaUsuario()) )
//                .idRol(Rol.builder().idRol(2).build())//rol de administrador
//                .build();
//        userRepository.save(usuario);
//        return "Administrador creado con éxito";
//    }
}
