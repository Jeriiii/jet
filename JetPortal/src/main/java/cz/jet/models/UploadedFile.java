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
import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {

	/**
	 * POM file for form
	 */
	private MultipartFile file;
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

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
