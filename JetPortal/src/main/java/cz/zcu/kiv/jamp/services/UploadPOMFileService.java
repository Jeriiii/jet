/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.jamp.services;

import cz.zcu.kiv.jamp.services.exceptions.NotCreatedDirException;
import cz.zcu.kiv.jamp.models.UploadedFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Upload POM file on server
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
@Service
public class UploadPOMFileService {

	/**
	 * path where to store file, set in config.properties
	 */
	@Value("${filesPath}")
	private String path;

	/**
	 * Upload POM file
	 *
	 * @param uploadedFile POM file
	 * @param fileName New pom file name
	 * @throws IOException
	 * @throws NotCreatedDirException Directory wasn't created
	 */
	public void upload(UploadedFile uploadedFile, String fileName) throws IOException, NotCreatedDirException {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			// save file on disk
			inputStream = uploadedFile.getInputStream();
			File newFile = new File(path + "poms/" + fileName + ".xml");
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} finally {

			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}
