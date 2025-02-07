package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.entity.PasswordResetToken;
import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.infraestructure.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public PasswordResetToken createResetTokenForUser(Usuario usuario, String token) {
        //String token = UUID.randomUUID().toString(); // Generar un token único
        PasswordResetToken passW = PasswordResetToken.builder()
                .token(token)
                .usuario(usuario)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();
        System.out.println("Token del serviicio: " + passW.getToken());
        return tokenRepository.save(passW);// Guardar el token en la base de datos
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));
        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }
        return passwordResetToken.getUsuario().getEmailUsuario();
    }

    public void deletePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token no encontrado"));
        tokenRepository.delete(passwordResetToken);
    }
}
