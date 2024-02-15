package com.nobroker.service;

// Import necessary packages

import com.nobroker.entity.User;
import com.nobroker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ForgotPasswordService {

    @Autowired
    private JavaMailSender javaMailSender; // Inject JavaMailSender bean

    @Autowired
    private UserRepository userRepository; // Inject UserRepository bean

    @Autowired
    private EmailService emailService;

    public void sendOTPEmail(User user) {
        String otp = emailService.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiration(LocalDateTime.now().plusMinutes(15)); // OTP valid for 15 minutes
        userRepository.save(user);

        // Send email with OTP
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Password Reset OTP");
        mailMessage.setText("Your OTP for password reset is: " + otp);

        javaMailSender.send(mailMessage);
    }

    public void resetPassword(String email, String otp, String newPassword) {
        // Validate OTP and update password
        User user = userRepository.findByEmailAndOtp(email, otp);
        if (user != null && user.getOtpExpiration().isAfter(LocalDateTime.now())) {
            user.setPassword(newPassword);
            user.setOtp(null);
            user.setOtpExpiration(null);
            user.setEmailVerified(true); // You may update this based on your logic
            userRepository.save(user);
        } else {
            // Handle invalid OTP or expired OTP
            throw new RuntimeException("Invalid OTP or expired OTP");
        }
    }
}

