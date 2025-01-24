package com.gym.gym_ver2.infraestructure.controller;

import com.gym.gym_ver2.infraestructure.auth.AuthResponse;
import com.gym.gym_ver2.infraestructure.auth.AuthService;
import com.gym.gym_ver2.infraestructure.auth.LoginRequest;
import com.gym.gym_ver2.infraestructure.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;//para acceder a los metodos y a su ves al token

//    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/login")
    public ResponseEntity<AuthResponse>  login(@RequestBody LoginRequest rq) {
        return ResponseEntity.ok(authService.login(rq));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest rq) {
        return ResponseEntity.ok(authService.register(rq));
    }
}
