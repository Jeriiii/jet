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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josef Hula
 */

@Service
public class MailService {
    
    @Value("${resultAddress}")
    private String resultAddress; // result address, set in config.properties
    
    @Autowired
    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
    }
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
                 mimeMessage.setSubject("Výsledek validace POM souboru");
                 mimeMessage.setFrom(new InternetAddress("hula.josef@gmail.com"));
                 mimeMessage.setText(resultAddress + fileName);
            }
       };
       try {
            mailSender.send(preparator);
       }
        catch(MailException ex) {
            System.err.println(ex.getMessage());            
        }
    }
}