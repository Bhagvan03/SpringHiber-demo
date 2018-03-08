package com.decipher.usermanage.util.mail;

import com.decipher.usermanage.entities.Account;
import com.decipher.usermanage.util.DataBaseConfig;
import com.decipher.usermanage.util.UserLogger;
import com.decipher.usermanage.util.cryptography.CriptographyUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Email sending ( for registration account ,recovery account etc.)
 *
 */

public class EmailSenderService {

	/***
	 * configuration mail api properties
	 */
	private static final String LINK;
	private static final String SMTP_HOST_NAME = "sg2plcpnl0006.prod.sin2.secureserver.net"; //or simply "localhost"
	private static final String SMTP_AUTH_USER = "app_user@decipherzone.com";
	private static final String SMTP_AUTH_PWD = "decipher2015";
	private static final String EMAIL_FROM_ADDRESS = "app_user@decipherzone.com";

	private EmailSenderService() {

	}

	/****
	 * set LINK mode {local,on perticular Ip} according to server status
	 * using static intializer block
	 */
	static {
		if (DataBaseConfig.SERVER_STATUS_MODE.equals(1)) {
			LINK = "http://localhost:8080/UserManagement/conformUser?emailId=";

		} else if (DataBaseConfig.SERVER_STATUS_MODE.equals(2)) {
			LINK = "http://192.168.0.104:8080/UserManagement/conformUser?emailId=";
		} else {
			LINK = "http://192.168.0.209:8080/UserManagement/conformUser?emailId=";
		}
	}

	/***
	 * Email sender service
	 * @param account
	 * @return
	 */
	public static Boolean mailingService(Account account) {
		String emailId = account.getEmail();
		String applicationLink = "<a href=" + LINK + CriptographyUtil.encrypt(emailId) + ">click here</a>";
		String msgText = getWelcomeMessage(account.getUserName(), account.getPassword(), emailId, applicationLink);
		UserLogger.info("msgText : " + msgText);
		return sendEmail(account.getEmail(), "Verification", msgText);
	}

	/****
	 * message mailing Properties Initializer method
	 * @return
	 */
	private static Session messagePropertiesInitializer() {
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", SMTP_AUTH_USER);
			props.put("mail.smtp.password", SMTP_AUTH_PWD);
			props.put("mail.smtp.port", "465");
			props.put("mail.smtps.auth", "true");
			return Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(
							EMAIL_FROM_ADDRESS, SMTP_AUTH_PWD);// Specify the Username and the PassWord
				}
			});
		} catch (Exception e) {
			UserLogger.error(e);
		}
		return null;
	}

	/***
	 * sending E-mail
	 * @param to
	 * @param subject
	 * @param text
	 * @return
	 */
	private static boolean sendEmail(String to, String subject, String text) {
		boolean status = false;
		try {
			Session session = messagePropertiesInitializer();
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = new InternetAddress(EMAIL_FROM_ADDRESS, "User Management");
			InternetAddress toAddress = new InternetAddress(to);
			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(subject);
			message.setContent(text, "text/html");
			if (session != null) {
				Transport transport = session.getTransport("smtps");
				transport.connect(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				UserLogger.info("mail has been sent");
				status = true;
			}

		} catch (UnsupportedEncodingException | MessagingException | NullPointerException e) {
			throw new UsernameNotFoundException("Mail don't send" + e);
		}
		return status;
	}

	/***
	 * create mailing message for e-mail receiver user
	 * @param username
	 * @param emailId
	 * @param applicationLink
	 * @return
	 */
	private static String getWelcomeMessage(String username, String password, String emailId, String applicationLink) {

		return "Dear "
				+ username
				+ "<br/><br/>You have successfully registered with User Management Application and Your Account Details are following : <br>"
				+ "<br/><b>Email Id : " + emailId + "<br>"
				+ "<br/><b>Password : " + password + "<br>"
				+ "<br/><br/><br/>To verify your account  <b>" + emailId
				+ "</b><br/><br/>Please click on below link to activate your account :<br/>"
				+ applicationLink + "<br><br><br>Regards" + " :  "
				+ "User Management Application Service Team ";
	}
}