/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class MvnProcessBuilderTest {

	public MvnProcessBuilderTest() {
	}

	@Test
	public void testStart() throws Exception {

		String programName = "/Users/josefhula/apache-maven-3.2.1/bin/mvn";
		String programPath = "cz.slezacek.ccp3:compatibility-checking-plugin:check";
		String checkPath = "/Users/josefhula/Dev/HelloWorld-maven/pom.xml";

		List<String> params = new LinkedList<String>();

		params.add(programName);
		//params.add("exec: java");
		params.add(programPath);
		//params.add("start");
		params.add("-f");
		params.add(checkPath);

		MvnProcessBuilder mpb = new MvnProcessBuilder(params);

		try {
			//mpb.start();
			Process process = mpb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
			  System.out.println(line);
			}
			System.out.println("Program terminated!");
		} catch (Error e) {
			Logger.getLogger(MvnProcessBuilder.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}