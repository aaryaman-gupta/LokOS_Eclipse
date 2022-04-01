package util.mail;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.File;

import java.util.*;

//mvn exec:java -Dexec.mainClass="mail.ZipAndSendMail"  
public class ZipAndSendMail {
	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;
	static String[] toEmails = { "aaryamantesting@gmail.com","daaryamangupta@gmail.com"};
//	static String[] toEmails = { "aaryamantesting@gmail.com"};
	static String fromUser = "aaryamantesting@gmail.com";// imap on, other devices on, no recovery phone
	static String password = "aaryamantesting123";

	public static void send(String sub, String body) throws Exception {

		// report folder - extent reports

		String reportFolder = System.getProperty("user.dir") + "//reports//";
		// find latest folder

		File dir = new File(reportFolder);
		File[] files = dir.listFiles();
		File lastModified = Arrays.stream(files).filter(File::isDirectory).max(Comparator.comparing(File::lastModified))
				.orElse(null);
		System.out.println(lastModified.getName());

		// zip
		Zip.zipDir(reportFolder + "\\" + lastModified.getName(), reportFolder + "\\" + lastModified.getName() + ".zip");

		// mail

		Mail javaEmail = new Mail();

		javaEmail.setMailServerProperties();

		javaEmail.createEmailMessage(sub, // subject
				body, // body
				reportFolder + "\\" + lastModified.getName() + ".zip", // attachment path
				"Reports.zip", // name of attachment
				toEmails// receivers
		);
		javaEmail.sendEmail(fromUser, password);

	}

}