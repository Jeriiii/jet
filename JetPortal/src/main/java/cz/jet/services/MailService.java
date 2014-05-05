/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * Service responsible for sending the result to email.
 * @author Josef Hula
 */

@Service
public class MailService {
    
    private static final Logger log = Logger.getLogger(MailService.class.getName());
	
    @Value("${resultAddress}")
    private String resultAddress; // result address, set in config.properties
    
    @Value("${emailSubject}")
    private String emailSubject; // email subject, set in config.properties
    
    @Value("${emailFrom}")
    private String emailFrom; // email address of sender, set in config.properties
    
    @Autowired
    private JavaMailSender mailSender;
	
    /**
     * Send mail with result
     * @param email recipient adress
     * @param result result of validation
     * @param id identification number of result
     */
    public void sendMail(final String email, final String fileName) {
        
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO,
						new InternetAddress(email));
				mimeMessage.setSubject(emailSubject);
				mimeMessage.setFrom(new InternetAddress(emailFrom));
				mimeMessage.setText("Hi, \n"
                                                    + "on this link you can check result of POM file validation "
                                                    + resultAddress + "/result/result?id=" + fileName
                                                    + " \nCheers.");
			}
		};
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			log.log(Level.SEVERE, "an exception was thrown", ex);
		}
    }
}