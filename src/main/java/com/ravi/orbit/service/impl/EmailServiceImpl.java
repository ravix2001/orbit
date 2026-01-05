package com.ravi.orbit.service.impl;

import com.ravi.orbit.entity.User;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.UserRepository;
import com.ravi.orbit.service.IEmailService;
import com.ravi.orbit.service.IUserService;
import com.ravi.orbit.utils.CommonMethods;
import com.ravi.orbit.utils.MyConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender javaMailSender;
    private final IUserService userService;
    private final UserRepository userRepository;
    private final Map<String, String> verificationTokens = new HashMap<>(); // Temporary store for tokens

    @Override
    public String sendVerificationEmail(String email) {
        if (CommonMethods.isEmpty(email)) {
            throw new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + "Email: " + email);
        }

        // Generate a verification token
        String token = UUID.randomUUID().toString();
        verificationTokens.put(email, token);

        // Build verification link
//        String verificationLink = "http://your-domain.com/api/verify-email?token=" + token;
        String verificationLink = "http://localhost:8080/api/verify-email?token=" + token;
        // Send verification email
        String subject = "Verify Your Email Address";
        String body = "Click the link below to verify your email:\n" + verificationLink;
        sendEmail(email, subject, body);
        log.info("Verification email sent to {}", email);
        return "Verification email sent to " + email;
    }

    // fix this later
    @Override
    public String sendForgotPasswordEmail(String email){
        return null;
    }

    // Verify the email using the token
    @Override
    public String verifyEmailForUser(String token) {
        try{
            for (Map.Entry<String, String> entry : verificationTokens.entrySet()) {
                if (entry.getValue().equals(token)) {
                    // Mark the email as verified
                    String email = entry.getKey();
                    User user = userService.getUserByEmail(email);
                    if (user != null) {
                        user.setStatus(EStatus.ACTIVE);
//                    user.setVerified(true); // Assumes a 'verified' field exists in the User entity
                        userRepository.save(user);
                    }

                    // Remove token after verification
                    verificationTokens.remove(email);
                }
            }
        }catch (Exception e){
            log.error("Error verifying email: {}", e.getMessage());
            throw new BadRequestException("Error verifying email");
        }
        return "Email verified successfully";
    }

    @Override
    public void sendEmail(String to, String subject, String body){
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);

            javaMailSender.send(mail);
        }catch (MailException e){
            log.error("Error sending email to {}: {}", to, e.getMessage());
            throw new MailSendException("Error while sending mail");
        }
    }

}
