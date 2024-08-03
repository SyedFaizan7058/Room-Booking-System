package com.hotel_managment;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailNotification {

	private static final String FROM_EMAIL = "syedfaizu7058@gmail.com";
	private static final String PASSWORD = "vuqv sums drfp kplh";

	public static void sendNotification(String toEmail, String subject, String body) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(FROM_EMAIL));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

			message.setSubject(subject);

			message.setText(body);

			Transport.send(message);

			System.out.println("\nEmail sent successfully.");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}