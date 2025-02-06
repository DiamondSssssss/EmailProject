package com.example.EmailProject.service.impl;

import com.example.EmailProject.dto.UserDto;
import com.example.EmailProject.repository.UserRepository;
import com.example.EmailProject.service.MailService;
import com.example.EmailProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private final MailService mailService;

    public UserServiceImpl(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        // Gán UUID cho user
        userDto.setUserId(UUID.randomUUID());

        try {
            // Gửi email "Contact Us" đến mail của người admin
            String subject1 = "Contact Us";
            String content1 = "Từ website: " + userDto.getOfWebsite() + "<br><br>"
                    + "Tên khách hàng: " + userDto.getName() + "<br>"
                    + "Email của khách: " + userDto.getEmail() + "<br>"
                    + "Nội dung: " + (userDto.getMessage() != null ? userDto.getMessage() : "Không có nội dung.");

            mailService.sendEmail("trongtri140604@gmail.com", subject1, content1);


            // Gửi email "Đã nhận" đến khách hàng
            String subject2 = "Đã nhận";
            String content2 = "Cảm ơn khách hàng đã tương tác.";
            mailService.sendEmail("tribeobungbu120@gmail.com", subject2, content2);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage(), e);
        }

        return userDto;
    }
}
