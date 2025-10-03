package com.mokaya.tikio.services.user;

import com.mokaya.tikio.models.Profile;
import com.mokaya.tikio.models.User;
import com.mokaya.tikio.enums.Role;
import com.mokaya.tikio.enums.UserStatus;
import com.mokaya.tikio.repositories.ProfileRepository;
import com.mokaya.tikio.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
    }

    @Override
    public User registerUser(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email address already in use.");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(username + " is already in use.");
        }

        User user = User.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER) // default role
                .status(UserStatus.ACTIVE)
                .enabled(true)
                .build();

        // Persist the user first so it has an id for the profile's foreign key
        User savedUser = userRepository.save(user);

        Profile profile = new Profile();
        profile.setUser(savedUser); // link profile to the persisted user
        // set both sides for consistency
        savedUser.setProfile(profile);

        // Now save the profile (profile is the owning side)
        profileRepository.save(profile);

        return savedUser;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User " + username + " not found"));
    }

    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
