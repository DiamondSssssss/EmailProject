package com.example.EmailProject.service.impl;

import com.example.EmailProject.dto.UserDto;
import com.example.EmailProject.repository.UserRepository;
import com.example.EmailProject.service.MailService;
import com.example.EmailProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private final MailService mailService;
    private final Map<String, Long> emailSentMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public UserServiceImpl(MailService mailService) {
        this.mailService = mailService;

        // Xóa email khỏi danh sách sau 5 phút để cho phép gửi lại
        scheduler.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            emailSentMap.entrySet().removeIf(entry -> now - entry.getValue() > 5 * 60 * 1000);
        }, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        String email = userDto.getEmail();
        long currentTime = System.currentTimeMillis();

        // Kiểm tra nếu email đã gửi trong 5 phút gần đây
        if (emailSentMap.containsKey(email) && (currentTime - emailSentMap.get(email)) < 5 * 60 * 1000) {
            throw new RuntimeException("Vui lòng thử lại sau 5 phút.");
        }

        // Cập nhật trạng thái email đã gửi
        emailSentMap.put(email, currentTime);

        // Gán UUID cho user
        userDto.setUserId(UUID.randomUUID());

        try {
            // Gửi email "Contact Us" đến admin
            String subject1 = "Contact Us";
            String content1 = "Từ website: " + userDto.getOfWebsite() + "<br><br>"
                    + "Tên khách hàng: " + userDto.getName() + "<br>"
                    + "Email của khách: " + userDto.getEmail() + "<br>"
                    + "Nội dung: " + (userDto.getMessage() != null ? userDto.getMessage() : "Không có nội dung.");

            mailService.sendEmail("trongtri140604@gmail.com", subject1, content1);

            // Gửi email "Đã nhận" đến khách hàng với giao diện tấm thiệp
            String subject2 = "🌟 Cảm ơn quý khách đã liên hệ! 🌟";
            String content2 = "<div style='background: url(https://png.pngtree.com/background/20210711/original/pngtree-thank-you-letter-bulletin-board-display-board-background-material-picture-image_1074909.jpg) no-repeat center center; "
                    + "background-size: cover; padding: 40px; text-align: center; color: #333; "
                    + "font-family: Arial, sans-serif; border-radius: 15px; max-width: 600px; margin: auto; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);'>"
                    + "<div style='background: rgba(255, 255, 255, 0.8); padding: 30px; border-radius: 10px;'>"
                    + "<img src='https://static.vecteezy.com/system/resources/previews/008/513/899/non_2x/blue-diamond-illustration-png.png' width='100' style='margin-bottom: 10px;'>"
                    + "<h2 style='color: #2C3E50;'>💌 Cảm ơn quý khách đã liên hệ! 💌</h2>"
                    + "<p style='font-size: 18px;'><b>Xin chào, " + userDto.getName() + "!</b></p>"
                    + "<p style='font-size: 16px;'>Chúng tôi đã nhận được yêu cầu của bạn và sẽ phản hồi trong thời gian sớm nhất.</p>"
                    + "<p style='font-size: 16px;'>Nếu có bất kỳ câu hỏi nào, vui lòng liên hệ lại với chúng tôi.</p>"
                    + "<hr style='border: 1px solid #ddd; margin: 20px;'>"
                    + "<p style='font-size: 14px; color: #555;'>📌 Trân trọng,</p>"
                    + "<p style='font-size: 14px; font-weight: bold; color: #2980B9;'>Đội ngũ hỗ trợ khách hàng</p>"
                    + "<p style='font-size: 14px; color: #777;'>Công ty XYZ</p>"
                    + "</div>"
                    + "</div>";

            mailService.sendEmail(userDto.getEmail(), subject2, content2);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage(), e);
        }

        return userDto;
    }
}
