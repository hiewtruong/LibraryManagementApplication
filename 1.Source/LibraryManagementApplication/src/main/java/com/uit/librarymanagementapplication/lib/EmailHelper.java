package com.uit.librarymanagementapplication.lib;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookSendEmail;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionSendEmail;
import java.text.SimpleDateFormat;
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

    public static String generateLoanEmailContent(TransactionSendEmail transaction) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        StringBuilder content = new StringBuilder();
        content.append("<html><body>");
        content.append("<h3>Hello ").append(transaction.getUseName()).append(",</h3>");
        content.append("<p>Your book loan request has been successfully recorded. Below are the loan details:</p>");

        content.append("<ul>");
        content.append("<li><strong>Loan Ticket Number:</strong> ").append(transaction.getLoanTicketNumber()).append("</li>");
        content.append("<li><strong>Email:</strong> ").append(transaction.getEmail()).append("</li>");
        content.append("<li><strong>Phone:</strong> ").append(transaction.getPhone()).append("</li>");
        content.append("<li><strong>Total Quantity:</strong> ").append(transaction.getTotalQty()).append("</li>");
        content.append("<li><strong>Loan Date:</strong> ").append(sdf.format(transaction.getLoanDt())).append("</li>");
        content.append("<li><strong>Return Date:</strong> ").append(sdf.format(transaction.getLoanReturnDt())).append("</li>");
        content.append("</ul>");

        content.append("<h4>Book Details</h4>");
        content.append("<table border='1' cellspacing='0' cellpadding='5' style='border-collapse: collapse;'>");
        content.append("<tr style='background-color: #f2f2f2;'>");
        content.append("<th>ID</th><th>Title</th><th>Author</th>");
        content.append("</tr>");

        for (BookSendEmail book : transaction.getBookDetails()) {
            content.append("<tr>");
            content.append("<td>").append(book.getBookID()).append("</td>");
            content.append("<td>").append(book.getTitle()).append("</td>");
            content.append("<td>").append(book.getAuthor()).append("</td>");
            content.append("</tr>");
        }

        content.append("</table>");

        // Footer
        content.append("<br><hr>");
        content.append("<p style='font-size: 12px; color: gray;'>This is an automated message. Please do not reply to this email.</p>");
        content.append("</body></html>");

        return content.toString();
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
