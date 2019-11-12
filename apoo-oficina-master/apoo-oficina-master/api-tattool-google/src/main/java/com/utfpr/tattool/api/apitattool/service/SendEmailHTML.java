package com.utfpr.tattool.api.apitattool.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailHTML {

	// for example, smtp.mailgun.org
	private static final String SMTP_SERVER = "smtp.gmail.com";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";

	private static final String EMAIL_FROM = "jv.adrenalina@gmail.com";
	private static final String EMAIL_TO = "email_1@yahoo.com, email_2@gmail.com";
	private static final String EMAIL_TO_CC = "";

	private static final String EMAIL_SUBJECT = "Bem vindo ao TATTOOL";
	private static final String EMAIL_TEXT = "<h1>Olá, bem vindo {nome}</h1>";

	public static void sendEmail(String nome, String email) {
		 // Recipient's email ID needs to be mentioned.
        String to = email;

        // Sender's email ID needs to be mentioned
        String from = EMAIL_FROM;

        // Assuming you are sending email from through gmails smtp
        String host = SMTP_SERVER;

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("jv.adrenalina@gmail.com", "88072567JvGa");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(EMAIL_SUBJECT);

            // Now set the actual message
            message.setText("Olá, bem vindo "+nome);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		SendEmailHTML.sendEmail("João", "jv.goulart.almeida@hotmail.com");
	}
}
