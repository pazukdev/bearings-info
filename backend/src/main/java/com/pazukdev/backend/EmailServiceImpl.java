package com.pazukdev.backend;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServiceImpl {

    public static void main(String args[]){

        String from = "old.vehicles.app@gmail.com";
        String to = "sverch@tut.by";
        String host = "127.0.0.1";

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props); // default session

        try {
            MimeMessage message = new MimeMessage(session); // email message

            message.setFrom(new InternetAddress(from)); // setting header fields

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Test Mail from Java Program"); // subject line

            // actual mail body
            message.setText("You can send mail from Java program by using mail API, but you need" +
                    "couple of more JAR files e.g. smtp.jar and activation.jar");

            // Send message
            Transport.send(message); System.out.println("Email Sent successfully....");
        } catch (MessagingException mex){ mex.printStackTrace(); }

    }

//    public static void main(String[] args) {
//
//        final String username = "old.vehicles.app@gmail.com";
//        final String password = "19851985admin";
//
//        Properties props = new Properties();
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("old.vehicles.app@gmail.com"));
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse("old.vehicles.app@gmail.com"));
//            message.setSubject("Testing Subject");
//            message.setText("Dear Mail Crawler,"
//                    + "\n\n No spam to my email, please!");
//
//            Transport.send(message);
//
//            System.out.println("Done");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
