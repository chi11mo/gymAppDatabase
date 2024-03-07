package com.chillmo.gymappdatabase.mail.service;

import com.chillmo.gymappdatabase.users.domain.User;
import com.chillmo.gymappdatabase.users.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private TokenRepository tokenRepository;

    public void sendVerificationEmail(User user, String siteURL) {
        String subject = "Please verify your registration";
        String senderName = "Your Company Name";
        String mailContent = "<p>Dear " + user.getEmail() + ",</p>";
        mailContent += "<p>Please click the link below to verify your registration:</p>";
        //tokenRepository.findTokenByUser(user).getTokenContent();
        String verifyURL = siteURL + "/verify?token=" + user.getToken().getTokenContent();

        mailContent += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";
        mailContent += "<p>Thank you<br>Your Company Name</p>";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(mailContent);
       // mailSender.send(email);
    }
}

