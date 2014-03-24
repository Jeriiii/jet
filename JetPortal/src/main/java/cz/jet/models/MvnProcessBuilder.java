/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.models;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class MvnProcessBuilder {

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
}