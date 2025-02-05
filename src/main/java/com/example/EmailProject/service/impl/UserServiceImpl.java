package com.example.EmailProject.service.impl;

import com.example.EmailProject.dto.UserDto;
import com.example.EmailProject.entity.User;
import com.example.EmailProject.mapper.UserMapper;
import com.example.EmailProject.repository.UserRepository;
import com.example.EmailProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        User user2= new User();

        User saveduser = userRepository.save(user);
        UserDto retrieveduser = UserMapper.toDto(saveduser);
        return retrieveduser;
    }
}
