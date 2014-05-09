package cz.jet.services;

import cz.jet.controllers.UploadController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Copy example file to folder poms
 *
 * @author Petr Kukrál
 */
@Service
public class InstalFileService {

	/**
	 * path where to store file, set in config.properties
	 */
	@Value("${filesPath}")
	private String path;

	private static final Logger log = Logger.getLogger(UploadController.class.getName());

	@PostConstruct
	public void init() {
		File exampleFile = new File(path + "poms/example-pom.xml");
		if (!exampleFile.exists()) {

			// copy example file to dir results
			InputStream is = null;
			OutputStream os = null;
			try {
				is = this.getClass().getResourceAsStream("/example-pom.xml");
				os = new FileOutputStream(exampleFile);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
			} catch (FileNotFoundException ex) {
				log.log(Level.SEVERE, "an exception was thrown", ex);
			} catch (IOException ex) {
				log.log(Level.SEVERE, "an exception was thrown", ex);
			} finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException ex) {
					log.log(Level.SEVERE, "an exception was thrown", ex);
				}
				try {
					if (os != null) {
						os.close();
					}
				} catch (IOException ex) {
					log.log(Level.SEVERE, "an exception was thrown", ex);
				}
			}
		}
	}
}
