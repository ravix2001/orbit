package com.ravi.orbit.service;

public interface IEmailService {

    String sendVerificationEmail(String email);
    String sendForgotPasswordEmail(String email);
    String verifyEmail(String token);
    void sendEmail(String to, String subject, String content);

}
