/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.services;

import cz.jet.utils.MvnProcessBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    
    @Autowired
    private MailService mailer;
    
    @Autowired
    private PomItemsService pomItemsService;
    
    @Autowired
    private MvnProcessBuilder mvnProcess;
    
    @Value("${filePath}")
    private String path; // path where is the file stored, set in config.properties
    
    @Value("${mavenPath}")
    private String mavenPath; // path to maven, set in config.properties
    
    @Value("${pluginParam}")
    private String pluginParam; // path to validator plugin, set in config.properties
    
    @Async
    public void validatePom(String fileName, String email, long id) throws IOException{
        StringBuilder sb = new StringBuilder();
        List<String> params = new ArrayList<String>();
        params.add(mavenPath);
        params.add(pluginParam);
        params.add("-f");
        params.add(path+fileName);
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
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
                
            String resultTest = sb.toString();
            
            pomItemsService.updateResult(resultTest, id);
          
            mailer.sendMail(email, id); 
            
        } catch (Error e) {
            Logger.getLogger(MvnProcessBuilder.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            br.close();
        }
    }
}