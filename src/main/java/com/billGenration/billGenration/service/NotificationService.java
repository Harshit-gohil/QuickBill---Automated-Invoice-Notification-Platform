package com.billGenration.billGenration.service;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.File;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Value("${twilio.whatsapp.number}")
    private String twilioWhatsappNumber;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSms(String to, String message) {

        Twilio.init(accountSid, authToken);
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioPhoneNumber),
                message
        ).create();
    }

    public void sendWhatsApp(String to, String message) {

        Twilio.init(accountSid, authToken);

        System.out.println("Sending WhatsApp to: " + to);

        Message msg = Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber(twilioWhatsappNumber),
                message
        ).create();

        System.out.println("WhatsApp SID: " + msg.getSid());
    }
    public void sendEmailWithAttachment(String toEmail,
                                        String subject,
                                        String text,
                                        String filePath) {

        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file =
                    new FileSystemResource(new File(filePath));

            helper.addAttachment("Invoice.csv", file);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Email sending failed");
        }
    }
}
