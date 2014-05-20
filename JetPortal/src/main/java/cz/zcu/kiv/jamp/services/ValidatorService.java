package cz.zcu.kiv.jamp.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service responsible for validating the POM file
 *
 * @author Josef Hula
 */
@Service
public class ValidatorService {

	private static final Logger log = Logger.getLogger(ValidatorService.class.getName());

	/**
	 * Service for sending mail
	 */
	@Autowired
	private MailService mailer;

	/**
	 * path, where is the file stored, set in config.properties
	 */
	@Value("${filesPath}")
	private String path;

	/**
	 * path to validator plugin, set in config.properties
	 */
	@Value("${pluginParam}")
	private String pluginParam;

	/**
	 * Validation of POM file. Validate POM file and after validation send
	 * email, if email address was entered.
	 *
	 * @param fileName name of pom file
	 * @param email email address for sending result of validation
	 * @throws IOException
	 */
	@Async
	public void validatePom(String fileName, String email) throws IOException {
		PrintWriter resultFile = new PrintWriter(path + "results/" + fileName + ".txt", "UTF-8");
		String mavenPath = "mvn";
		List<String> params = new ArrayList<String>();
//		File file = new File(path + "results/" + fileName + ".txt");
		params.add(mavenPath); // set params for process builder
		params.add(pluginParam);
		params.add("-f");
		params.add(path + "poms/" + fileName + ".xml");
		InputStream is;
		InputStreamReader isr;
		BufferedReader br = null;

		try {
			ProcessBuilder pb = new ProcessBuilder(params);
			Process process = pb.start();
			is = process.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				resultFile.println(line);
				resultFile.flush();
			}

			if (!email.equals("")) {
				mailer.sendMail(email, fileName);
			}
			process.waitFor();
			resultFile.println(DeferredReadService.END_SYMBOL);
		} catch (Error ex) {
			log.log(Level.SEVERE, "an exception was thrown", ex);
		} catch (InterruptedException ex) {
			log.log(Level.SEVERE, null, ex);
		} finally {
			resultFile.close();
			br.close();
		}
	}
}
