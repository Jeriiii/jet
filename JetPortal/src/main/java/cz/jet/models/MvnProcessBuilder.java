/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class MvnProcessBuilder {

    @Autowired 
    private ApplicationContext context;
	
	ProcessBuilder pb;

	public MvnProcessBuilder(List<String> list) {
		pb = new ProcessBuilder(list);
	}

	public MvnProcessBuilder(String list) {
		pb = new ProcessBuilder(list);
	}

	public Process start() throws MvnProcessBuilderError, IOException {
		try {
			return pb.start();
		} catch (IOException ex) {
			throw ex;
		}
	}
        
        
        public static String validate(String name) throws IOException {
            StringBuilder sb = new StringBuilder();
            String path = "/Users/josefhula/jet/files/";
            List<String> params = new ArrayList<String>();
            params.add("/Users/josefhula/apache-maven-3.2.1/bin/mvn");
            params.add("cz.slezacek.ccp3:compatibility-checking-plugin:check");
            params.add("-f");
            params.add(path+name);
            
            MvnProcessBuilder mpb = new MvnProcessBuilder(params);
            
            try {
		Process process = mpb.start();
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
		}		
		System.out.println("Program terminated!");
            } catch (Error e) {
		Logger.getLogger(MvnProcessBuilder.class.getName()).log(Level.SEVERE, null, e);
            }
            return sb.toString();
        }
}