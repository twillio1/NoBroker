package com.nobroker.controller;

import com.nobroker.entity.User;
import com.nobroker.repository.UserRepository;
import com.nobroker.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private UserRepository userRepository;

  //  http://localhost:8085//forgot-password/send-otp?email=?
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTPEmail(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            forgotPasswordService.sendOTPEmail(user);
            return ResponseEntity.ok("OTP sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    //http://localhost:8085//forgot-password/reset-password?email=?&otp=?&newPassword
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String newPassword) {
        try {
            forgotPasswordService.resetPassword(email, otp, newPassword);
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
