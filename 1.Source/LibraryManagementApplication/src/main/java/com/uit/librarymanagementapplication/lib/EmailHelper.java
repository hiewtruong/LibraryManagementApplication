package com.uit.librarymanagementapplication.lib;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailHelper {

    public static String generateAccountEmailContent(String userName, String password) {
        return "<html><body>"
                + "<h3>Hello " + userName + ",</h3>"
                + "<p>Your account has been successfully created. Below are your login details:</p>"
                + "<ul>"
                + "<li><strong>Username:</strong> " + userName + "</li>"
                + "<li><strong>Password:</strong> " + password + "</li>"
                + "</ul>"
                + "<p>Wish you have a good time to use own-app booking books.</p>"
                + "<br>"
                + "<hr>"
                + "<p style='font-size: 12px; color: gray;'>This is an automated message. Please do not reply to this email.</p>"
                + "</body></html>";
    }

    public static void sendEmail(String recipientEmail, String subject, String body) {
        final String senderEmail = "hieutg02198@gmail.com";
        final String senderPassword = "nekmikwmnfkzubas";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=UTF-8");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
