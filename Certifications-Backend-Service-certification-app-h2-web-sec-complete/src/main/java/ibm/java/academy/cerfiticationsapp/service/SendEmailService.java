package ibm.java.academy.cerfiticationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String to,String body, String topic){
		System.out.println("sending mail");
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom("certificateclassics@gmail.com");
		simpleMessage.setTo(to); //accepts multiple at once
		simpleMessage.setSubject(topic);
		simpleMessage.setText(body);

		javaMailSender.send(simpleMessage);
		System.out.println("email sent");
	}
}
