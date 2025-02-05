package com.example.EmailProject.mapper;

import com.example.EmailProject.dto.UserDto;
import com.example.EmailProject.entity.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getMessage(),
                user.getOfWebsite()
        );
    }

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setMessage(userDto.getMessage());
        user.setOfWebsite(userDto.getOfWebsite());
        return user;
    }
}