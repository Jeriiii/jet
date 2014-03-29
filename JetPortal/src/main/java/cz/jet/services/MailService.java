/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.services;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josef Hula
 */

public class MailService {
    
    @Autowired
    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
 
    public void sendMail(final String email, final long fileID) {
        
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                 mimeMessage.setRecipient(Message.RecipientType.TO, 
                     new InternetAddress(email));
                 mimeMessage.setFrom(new InternetAddress("hula.josef@gmail.com"));
                 mimeMessage.setText("Výsledek validace je možné zobrazit http://localhost:8080/result"+fileID);
            }
       };
       mailSender.send(preparator);
    }
}