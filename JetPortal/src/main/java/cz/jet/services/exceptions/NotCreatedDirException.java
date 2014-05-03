/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.services.exceptions;

/**
 * Exception for not created directory
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class NotCreatedDirException extends Exception {

	public NotCreatedDirException(String dirName) {
		super("Directory " + dirName + " wasn't created");
	}
}
