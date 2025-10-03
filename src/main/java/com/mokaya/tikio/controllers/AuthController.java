package com.mokaya.tikio.controllers;


import com.mokaya.tikio.dtos.JwtResponse;
import com.mokaya.tikio.dtos.LoginRequest;
import com.mokaya.tikio.dtos.UserRegistrationDto;
import com.mokaya.tikio.dtos.UserResponseDto;
import com.mokaya.tikio.models.User;
import com.mokaya.tikio.services.user.UserService;
import com.mokaya.tikio.utils.UserMapper;
import com.mokaya.tikio.utils.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        User user = userService.registerUser(
                userRegistrationDto.getUsername(),
                userRegistrationDto.getEmail(),
                userRegistrationDto.getPassword());
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Validate credentials
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user == null || !userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Generate JWT
        String token = jwtUtil.generateToken(user.getUsername());

        // Return token (you can also return DTO info if needed)
        return ResponseEntity.ok(new JwtResponse(token));
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

}
