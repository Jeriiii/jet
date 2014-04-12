/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.services;

import java.io.File;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Petr Kukrál
 */
@Service
public class PomFileService {
	
	@Value("${filePath}")
    public String path;
	
	@Value("${suffix}")
	public String suffix;
	
	@Value("${prefixWorking}")
	public String prefixWorking;
	
	@Value("${prefixFinish}")
	public String prefixFinish;
	
	public String getUniqueFileName() {
		String uuid = UUID.randomUUID().toString();
		String fileName;
		for(int i = 0;; i++) {
			fileName = "pom" + uuid + i;
			File f = new File(path + fileName + suffix);
			if(! f.exists()) {
				break;
			}
		}
		
		return fileName;
	}

}
