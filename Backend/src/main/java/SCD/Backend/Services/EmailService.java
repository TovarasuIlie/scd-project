package SCD.Backend.Services;

import SCD.Backend.Models.Delivery;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(Delivery delivery, String message, String title) throws MessagingException, IOException {
        MimeMessage email = mailSender.createMimeMessage();

        email.setFrom(new InternetAddress("niculai614@gmail.com"));
        email.setRecipients(MimeMessage.RecipientType.TO, delivery.getEmail());
        email.setSubject(title);
        email.setContent(message, "text/html; charset=utf-8");
        mailSender.send(email);
    }
}
