package com.gym.gym_ver2.infraestructure.auth;

import com.gym.gym_ver2.aplicaction.service.PasswordResetService;
import com.gym.gym_ver2.aplicaction.service.UsuarioService;
import com.gym.gym_ver2.domain.model.entity.Rol;
import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.infraestructure.jwt.JwtService;
import com.gym.gym_ver2.infraestructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements  AuthService {

    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PasswordResetService passwordResetService;
    private final UsuarioService usuarioService;
    private final JavaMailSender mailSender;

    public AuthResponse login(LoginRequest rq) {
        // Validar que el email y la contraseña no estén vacíos
        if (rq.getEmailUsuario() == null || rq.getEmailUsuario().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (rq.getContrasenaUsuario() == null || rq.getContrasenaUsuario().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        try {
            authenticationManager.authenticate(// autentica que el usuario y la contraseña sean correctos
                    new UsernamePasswordAuthenticationToken(rq.getEmailUsuario(), rq.getContrasenaUsuario())
            );
            //recuperar el usuario de la BD
            UserDetails userDetails = userRepository.findByEmailUsuario(rq.getEmailUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            HashMap<String, Object> aux = new HashMap<>(); //crear token con el usuario
            aux.put("sub", rq.getEmailUsuario());//agregar el email del usuario al token
            String token = jwtService.generateToken(aux, userDetails);
            return AuthResponse.builder().token(token).build();//crear la respuesta con el token
        } catch (Exception e) {
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
                .fechaNacimiento(rq.getFechaNacimiento())
                .horasRecompensas(rq.getHorasRecompensas())
                .numeroFicha(rq.getNumeroFicha())
                .contrasenaUsuario(passwordEncoder.encode(rq.getContrasenaUsuario()))
                .idRol(Rol.builder().idRol(3).build())//po defecto se asigna el rol de usuario
                .build();

        userRepository.save(usuario);//guardar el usuario en la base de datos
        //crear token con el usuario creado y retornar la respuesta
        return AuthResponse.builder().token(jwtService.createToken(usuario)).build();
    }

    public Usuario getUsuarioActual(String email) {
        return userRepository.findByEmailUsuario(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public String forgotPassword(String email) {
        // buscar al usuario por email
        System.out.println("Email del usuario: " + email);
        Usuario usuario = userRepository.findByEmailUsuario(email).orElseThrow(() -> new RuntimeException("No se encontró ningún usuario con este email"));
        String token = UUID.randomUUID().toString();// Generar un token único
        passwordResetService.createResetTokenForUser(usuario, token); // Guardar el token en la base de datos
        // enviar correo electrónico con el token
        String resetLink = "http://localhost:6090/auth/reset-password?token=" + token;
        sendPasswordResetEmail(usuario.getEmailUsuario(), resetLink);
        return "Se ha enviado un correo con instrucciones para restablecer tu contraseña.";
    }

    private void sendPasswordResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recuperación de Contraseña");
        message.setText("Haz clic en el siguiente enlace para restablecer tu contraseña: " + resetLink);
        mailSender.send(message);
    }

    @Override
    public String recoverPassword(String newPassword, String token) {
        //String email = passwordResetService.validatePasswordResetToken(newPassword);
        //Validar el token
        String email = passwordResetService.validatePasswordResetToken(token);
         usuarioService.updatePassword(email, newPassword);
        return "Contraseña actualizada correctamente";
    }

}
