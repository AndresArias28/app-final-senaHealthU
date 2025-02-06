package com.gym.gym_ver2.infraestructure.auth;

public interface AuthService  {
    AuthResponse login(LoginRequest rq);

    AuthResponse register(RegisterRequest rq);

    String forgotPassword(String email);

    String recoverPassword(String newPassword, String token);
}
