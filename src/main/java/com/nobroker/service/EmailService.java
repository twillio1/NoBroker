package com.nobroker.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.nobroker.service.EmailVerificationService.emailOtpMapping;

@Service
public class EmailService
{
    private JavaMailSender javaMailSender;

    private final UserService userService;

    public EmailService(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    public String generateOtp()
    {
        return String.format("%06d",new java.util.Random().nextInt(1000000));
    }

    public Map<String,String> sendOtpEmail(String email)
    {
        String otp = generateOtp();

        //save otp for the later verification
        emailOtpMapping.put(email, otp);

        sendEmail(email,"Otp for email Verification","Your OTP is: "+otp);

        Map<String,String> response=new HashMap<>();

        response.put("status","success");
        response.put("message","OTP sent successfully");
        return response;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
