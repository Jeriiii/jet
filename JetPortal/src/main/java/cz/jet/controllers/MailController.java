/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cz.jet.models.MvnProcessBuilder;
import cz.jet.models.MvnProcessBuilderError;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *
 * @author josefhula
 */

@Controller//anotace kontroleru
public class MailController
{
        @Autowired
	private MailSender mailSender;
        
        @RequestMapping(value = "mail", method = RequestMethod.GET) 
        public String sendMail(ModelMap model) throws MvnProcessBuilderError, IOException {//promenne do viewu
            //SendMailTest sm = new SendMailTest();
            //sm.send();
            
            String path = "/Users/josefhula/Dev/HelloWorld-maven/pom.xml";
            List<String> params = new ArrayList<String>();
            params.add("/Users/josefhula/apache-maven-3.2.1/bin/mvn");
            params.add("cz.slezacek.ccp3:compatibility-checking-plugin:check");
            params.add("-f");
            params.add(path);
            
            MvnProcessBuilder mpb = new MvnProcessBuilder(params);
            
            try {
		//mpb.start();
		Process process = mpb.start();
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null) {
		  System.out.println(line);
		}
                
                send("hula.josef@gmail.com",
    		   "josef.hula@hotmail.com",
    		   "Pom validator result", 
    		   "Testing only \n\n Hello Spring Email Sender");
                
		System.out.println("Program terminated!");
            } catch (Error e) {
		Logger.getLogger(MvnProcessBuilder.class.getName()).log(Level.SEVERE, null, e);
            }
            
            model.addAttribute("message", "Hotovo " + mpb);//promenna message predana do jspcka
            
            return "mail/mail";
        }
        
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
 
	public void send(String from, String to, String subject, String msg) {
 
		SimpleMailMessage message = new SimpleMailMessage();
 
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
	}
}