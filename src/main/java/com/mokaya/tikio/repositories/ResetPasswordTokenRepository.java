package com.mokaya.tikio.repositories;

import com.mokaya.tikio.models.ResetPasswordToken;
import com.mokaya.tikio.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {

    // Find by token
    Optional<ResetPasswordToken> findByToken(String token);

    // check if a user already has an active token()optional
    Optional<ResetPasswordToken> findByUser(User user);
}
