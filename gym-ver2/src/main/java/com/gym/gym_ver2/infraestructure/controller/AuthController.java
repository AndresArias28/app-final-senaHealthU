package com.gym.gym_ver2.infraestructure.controller;

import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.domain.model.pojos.AdminRegisterRequest;
import com.gym.gym_ver2.infraestructure.auth.AuthResponse;
import com.gym.gym_ver2.infraestructure.auth.AuthService;
import com.gym.gym_ver2.infraestructure.auth.LoginRequest;
import com.gym.gym_ver2.infraestructure.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;//instancia para acceder a los metodos y a su vez al token
    private final UserDetailsService userDetailsService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest rq) {
        return ResponseEntity.ok(authService.login(rq));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUsers(@RequestBody RegisterRequest rq) {
        return ResponseEntity.ok(authService.register(rq));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminRegisterRequest request, Authentication authentication) {

        Usuario usuarioActual = authService.getUsuarioActual(authentication.getName());

        System.out.println("Usuario actual: " + usuarioActual.getNombreUsuario());

        if (!usuarioActual.getIdRol().getIdRol().equals(1)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos para crear usuarios");
        }

//        // Verifica que el rol asignado al nuevo usuario es permitido
//        if (request.getRol().getNombreRol().equalsIgnoreCase("Superusuario")) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes asignar el rol de superusuario");
//        }
        return ResponseEntity.ok(authService.registerAdmin(request));
    }


}
