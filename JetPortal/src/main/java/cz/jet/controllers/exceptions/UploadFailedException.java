/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.controllers.exceptions;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class UploadFailedException extends Exception {

	public UploadFailedException(String msg) {
		super("Upload filed: " + msg);
	}

}
