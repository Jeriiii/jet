/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.services;

import cz.jet.models.UploadedFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
@Service
public class UploadPOMFileService {
    
        @Value("${filePath}")
        private String path; // path where to store file, set in config.properties
		
		@Value("${suffix}")
        private String suffix;
	
	public void upload(UploadedFile uploadedFile, String fileName) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			MultipartFile file = uploadedFile.getFile();
			// save file on disk
			inputStream = file.getInputStream();
			File newFile = new File(path + fileName + suffix);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch(IOException e) {
			throw e;
		} finally {
			if(inputStream != null)
				inputStream.close();
			if(outputStream != null)
				outputStream.close();
		}
		
		
	}
}