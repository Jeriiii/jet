package cz.jet.controllers;

import cz.jet.daos.impl.PomItemsDao;
import cz.jet.models.UploadedFile;
import cz.jet.services.ValidatorService;
import cz.jet.services.exceptions.NotCreatedDirException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for upload POM file
 *
 * @author Petr Kukrál
 */
// example http://www.javacodegeeks.com/2013/04/spring-mvc-form-tutorial.html
@Controller
public class UploadController {

	private static final Logger log = Logger.getLogger(UploadController.class.getName());

	/**
	 * POM validation
	 */
	@Autowired
	private ValidatorService validator;

	/**
	 * DAO for POM
	 */
	@Autowired
	private PomItemsDao pomDao;

	/**
	 * check if example result file exist
	 */
	private boolean checkedExampleFile = false;

	/**
	 * path where to store file, set in config.properties
	 */
	@Value("${filesPath}")
	private String path;

	/**
	 * Render for jsp with for
	 */
	@RequestMapping(value = "form-upload-file", method = RequestMethod.GET)
	public String loadFormPage(Model m) {
		m.addAttribute("uploadedFile", new UploadedFile());
		return "upload/formUploadFile";
	}

	/**
	 * Example for file upload
	 */
	@RequestMapping(value = "example-file-upload", method = RequestMethod.GET)
	public String exampleFileUpload(Model m) {
		if (this.checkedExampleFile == false) {
			File exampleFile = new File(path + "results/finish-example.txt");
			if (!exampleFile.exists()) {
				File sourceExampleFile = new File(path + "exampleresult.txt");
				// copy example file to dir results
				InputStream is = null;
				OutputStream os = null;
				try {
					is = new FileInputStream(sourceExampleFile);
					os = new FileOutputStream(exampleFile);
					byte[] buffer = new byte[1024];
					int length;
					while ((length = is.read(buffer)) > 0) {
						os.write(buffer, 0, length);
					}
				} catch (FileNotFoundException ex) {
					log.log(Level.SEVERE, null, ex);
				} catch (IOException ex) {
					log.log(Level.SEVERE, null, ex);
				} finally {
					try {
						if (is != null) {
							is.close();
						}
					} catch (IOException ex) {
						log.log(Level.SEVERE, null, ex);
					}
					try {
						if (os != null) {
							os.close();
						}
					} catch (IOException ex) {
						log.log(Level.SEVERE, null, ex);
					}
				}
			}
			this.checkedExampleFile = true;
		}

		String fileName = "example";
		m.addAttribute("id", fileName);
		return "redirect:/result";
	}

	/**
	 * Upload file
	 *
	 * @param email User e-mail
	 * @param uploadedFile POM file
	 */
	@RequestMapping(value = "form-upload-file", method = RequestMethod.POST)
	public String fileUploaded(@RequestParam("email") String email, Model m,
			UploadedFile uploadedFile, BindingResult result) {
		if (result.hasErrors()) {
			String errmsg = "";
			for (ObjectError err : result.getAllErrors()) {
				errmsg = errmsg + err.toString() + "<br />";
			}
			m.addAttribute("errorFormMessage", "File upload failed: <br />" + errmsg);
			return "upload/formUploadFile";
		}

		String fileName;

		// upload file
		try {
			fileName = pomDao.save(uploadedFile);
			m.addAttribute("successFormMessage", "File was successfully uploaded. After the validation you will receive email with link, where you can see the result of validation.");
		} catch (IOException ex) {
			log.log(Level.SEVERE, null, ex);
			m.addAttribute("errorFormMessage", "File upload failed: " + ex.getMessage());
			return "upload/formUploadFile";
		} catch (NotCreatedDirException ex) {
			log.log(Level.SEVERE, null, ex);
			m.addAttribute("errorFormMessage", "Server Error. File not be uploaded.");
			return "upload/formUploadFile";
		}

		// validation
		try {
			validator.validatePom(fileName, email);
		} catch (IOException ex) {
			log.log(Level.SEVERE, null, ex);
		}

		// redirect to validation site
		if (email == null || email.isEmpty()) {
			m.addAttribute("successFormMessage", null);
			m.addAttribute("id", fileName);
			return "redirect:/result";
		}
		return "upload/formUploadFile";
	}
}
