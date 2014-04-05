/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.services;

import cz.jet.controllers.UploadController;
import cz.jet.models.UploadedFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
@Service
public class UploadPOMFileService {
	
	public void upload(UploadedFile uploadedFile, String fileName) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		MultipartFile file = uploadedFile.getFile();
		// uložení souboru na disk
		inputStream = file.getInputStream();
		// cesta kam se ma soubor ulozit
		String path = "C:\\webhostJava\\files\\";
		//String path = "/Users/josefhula/jet/files/";
		File newFile = new File(path + fileName);
		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		outputStream = new FileOutputStream(newFile);
		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
	}
}
