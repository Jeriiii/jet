package cz.zcu.kiv.jamp.services;

import cz.zcu.kiv.jamp.controllers.UploadController;
import cz.zcu.kiv.jamp.services.exceptions.NotCreatedDirException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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

	/**
	 * check if POMS and RESULTS exist
	 */
	@PostConstruct
	public void init() {
		try {
			this.createDirIfNotExist(path, "poms/");
			this.createDirIfNotExist(path, "results/");
		} catch (NotCreatedDirException ex) {
			log.log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * If the directory does not exist, create it
	 *
	 * @param path Path to directory
	 * @param dirName Name of folder
	 * @throws NotCreatedDirException Directory wasn't created
	 */
	private void createDirIfNotExist(String path, String dirName) throws NotCreatedDirException {
		File dir = new File(path, dirName);

		if (!dir.exists()) {
			boolean result = dir.mkdir();

			if (!result) {
				throw new NotCreatedDirException(dirName);
			}
		}
	}
}
