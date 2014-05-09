/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.models;

/**
 * POM file entite for form
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {

	/**
	 * POM file for form
	 */
	private MultipartFile file = null;

	/**
	 * example POM file for form
	 */
	private File exampleFile = null;

	/**
	 * generated file name
	 */
	private String fileName;

	/**
	 * user e-mail
	 */
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public InputStream getInputStream() throws IOException {
		if (this.exampleFile != null) {
			return new FileInputStream(this.exampleFile);
		}
		return file.getInputStream();
	}

	public void setExampleFile(File file) {
		this.exampleFile = file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public MultipartFile getFile() {
		return file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
