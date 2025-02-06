package com.gym.gym_ver2.infraestructure.repository;

import com.gym.gym_ver2.domain.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken>  findByToken(String token);
    void deleteByToken(String token);
}
