package com.mycompany.util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static boolean sendVerificationCode(String recipientEmail, String code) {
        final String senderEmail = "thanhtuvi253@gmail.com";       // Thay bằng email của bạn
        final String senderPassword = "hfmzacaxoqybbmnl";        // Thay bằng App Password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Mã xác minh tài khoản");
            message.setText("Mã xác minh của bạn là: " + code);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }
}
