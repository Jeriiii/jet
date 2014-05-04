/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service responsible for validating the POM file
 * @author Josef Hula
 */

@Service
public class ValidatorService {
    
    private static final Logger log = Logger.getLogger(ValidatorService.class.getName());
	
    @Autowired
    private MailService mailer;
    
    @Value("${filesPath}")
    private String path; 
    
    @Value("${pluginParam}")
    private String pluginParam; // path to validator plugin, set in config.properties
    
    @Async
    public void validatePom(String fileName, String email) throws IOException{
	PrintWriter resultFile = new PrintWriter(path + "results/" + "working-" + fileName + ".txt", "UTF-8");
	//String mavenPath = "/Users/josefhula/apache-maven-3.2.1/bin/mvn";
        String mavenPath = "mvn.bat";
	//String mavenPath = "C:\\apache-maven-3.2.1\\bin\\mvn.bat";
        List<String> params = new ArrayList<String>();
        File file = new File(path + "results/" + "working-" + fileName + ".txt");
        params.add(mavenPath);
        params.add(pluginParam);
        params.add("-f");
        params.add(path + "poms/" + fileName + ".xml");
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        
        try {
            ProcessBuilder pb = new ProcessBuilder(params);
            Process process = pb.start();
            is = process.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                resultFile.println(line);
                resultFile.flush();
            }    
       
            if(!email.equals("")){
                mailer.sendMail(email, fileName); 
            }
            
        } catch (Error ex) {
            log.log(Level.SEVERE, null, ex);
        } finally {
            file.renameTo(new File(path + "results/" + "finish-" + fileName + ".txt")); 
            resultFile.close();
            br.close();
        }
    }
}