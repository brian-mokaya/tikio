package com.mokaya.tikio.utils;

import com.mokaya.tikio.dtos.UserResponseDto;
import com.mokaya.tikio.models.User;

public class UserMapper {

    public static UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setStatus(String.valueOf(user.getStatus()));
        dto.setRole(user.getRole().name());
        return dto;
    }
}
