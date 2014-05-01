/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.models;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class POMFile {
	
	public File uniquePOMFile;
	public File uniqueResultFile;
	private static final Logger log = Logger.getLogger(POMFile.class.getName());
	
	public POMFile(String path) {
		try {
			uniquePOMFile = File.createTempFile("pom", ".xml", new File(path));
			uniqueResultFile = new File(path + "working-" + uniquePOMFile.getName());
		} catch (IOException ex) {
			log.log(Level.SEVERE, null, ex);
		}
	}
	
	
	
}
