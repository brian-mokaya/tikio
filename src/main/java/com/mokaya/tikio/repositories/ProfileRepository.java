package com.mokaya.tikio.repositories;

import com.mokaya.tikio.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(Long userId);

    boolean existsByPhoneNumber(String phoneNumber);
}
