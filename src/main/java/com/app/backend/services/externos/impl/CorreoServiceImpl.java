package com.app.backend.services.externos.impl;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.app.backend.services.externos.ICorreoService;

import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorreoServiceImpl implements ICorreoService{

    @PersistenceContext
    private EntityManager entityManager;


    @Async
    @Override
    public void sendEmailAsync(String to, String subject, String body) {
        try {
            configurandoCorreo();
            enviarCorreo(to, subject, body);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }

    private JavaMailSenderImpl configurandoCorreo() {
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();

        emailSender.setHost("smtp.gmail.com");
        emailSender.setPort(587);
        emailSender.setUsername("brylombeida@gmail.com");
        emailSender.setPassword("fydk oose eczx ogak");

        Properties props = emailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return emailSender;
    }

    @SuppressWarnings("null")
    private void enviarCorreo(String to, String subject, String body) {
        try {
            JavaMailSenderImpl emailSender = configurandoCorreo();

            MimeMessage messageMime = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messageMime, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom("brylombeida@gmail.com", "Tramites estudiantiles");

            emailSender.send(messageMime);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}
