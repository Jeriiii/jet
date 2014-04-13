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
    
    @Autowired
    private MailService mailer;
    
    @Autowired
    private IPomItemsDao pomItemsService;
    
    @Autowired
    private MvnProcessBuilder mvnProcess;
    
	@Value("${resultPath}")
    private String resultPath;
	
	@Value("${prefixWorking}")
    private String prefixWorking;
	
	@Value("${prefixFinish}")
    private String prefixFinish;
        
    @Value("${suffix}")
    private String suffix;
	
    @Value("${filePath}")
    private String path; // path where is the file stored, set in config.properties
    
    @Value("${mavenPath}")
    private String mavenPath; // path to maven, set in config.properties
    
    @Value("${pluginParam}")
    private String pluginParam; // path to validator plugin, set in config.properties
    
    @Async
    public void validatePom(String fileName, String email) throws IOException{
        //StringBuilder sb = new StringBuilder();
		PrintWriter resultFile = new PrintWriter(resultPath + prefixWorking + fileName + ".txt", "UTF-8");
		
        List<String> params = new ArrayList<String>();
        File file = new File(resultPath + prefixWorking + fileName + ".txt");
        params.add(mavenPath);
        params.add(pluginParam);
        params.add("-f");
        params.add(path+fileName+suffix);
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
//                sb.append(line);
//                sb.append(System.getProperty("line.separator"));
            }
            file.renameTo(new File(resultPath + prefixFinish + fileName + ".txt"));    
            //String resultTest = sb.toString();
            
            //pomItemsService.updateResult(resultTest, id);
            
            mailer.sendMail(email, fileName); 
            
        } catch (Error e) {
            Logger.getLogger(MvnProcessBuilder.class.getName()).log(Level.SEVERE, null, e);
        } finally {
			resultFile.close();
            br.close();
        }
    }
}