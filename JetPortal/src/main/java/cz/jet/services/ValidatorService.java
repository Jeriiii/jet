/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.services;

import cz.jet.dao.IPomItemsDao;
import cz.jet.utils.MvnProcessBuilder;
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
 *
 * @author josefhula
 */

@Service
public class ValidatorService {
    
    private Logger log;
	
    @Autowired
    private MailService mailer;
    
    @Autowired
    private MvnProcessBuilder mvnProcess;
    
    @Value("${filesPath}")
    private String path;
    
    @Value("${pluginParam}")
    private String pluginParam; // path to validator plugin, set in config.properties
    
    @Async
    public void validatePom(String fileName, String email) throws IOException{
	PrintWriter resultFile = new PrintWriter(path + "results/" + "working-" + fileName + ".txt", "UTF-8");
	String mavenPath = "C:\\apache-maven-3.2.1\\apache-maven\\src\\bin\\mvn.bat";
        
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
            Process process = mvnProcess.start(params);
            is = process.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                resultFile.println(line);
                resultFile.flush();
            }
            file.renameTo(new File(path + "results/" + "finish-" + fileName + ".txt"));    
       
            if(!email.equals("")){
                mailer.sendMail(email, fileName); 
            }
            
        } catch (Error e) {
            log.getLogger(MvnProcessBuilder.class.getName()).log(Level.SEVERE, null, e);
        } finally {
			resultFile.close();
            br.close();
        }
    }
}