package cz.jet.controllers;

import cz.jet.models.UploadedFile;
import cz.jet.dao.IPomItemsDao;
import cz.jet.models.POMFile;
import cz.jet.services.PomFileService;
import cz.jet.services.ValidatorService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import cz.jet.services.UploadPOMFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.ObjectError;

/**
 *
 * @author Petr Kukrál
 */
 
// example http://www.javacodegeeks.com/2013/04/spring-mvc-form-tutorial.html
@Controller
public class UploadController {
    
    @Autowired
    private ValidatorService validator;
	
	@Autowired
    private UploadPOMFileService uploadPOMFile;
	
	@Autowired
    private PomFileService pomFileService;
	
	/**
	 * render for jsp with formulářem
	 */
	
	@RequestMapping(value="form-upload-file", method=RequestMethod.GET)
	public String loadFormPage(Model m) {
		m.addAttribute("uploadedFile", new UploadedFile());              
		return "upload/formUploadFile";
	}
    
	@RequestMapping(value="form-upload-file", method=RequestMethod.POST)
	public String fileUploaded(@RequestParam("email") String email,Model m,
			UploadedFile uploadedFile, BindingResult result) throws InterruptedException {
//		fileValidator.validate(uploadedFile, result);

		//String fileName = file.getOriginalFilename();

		if (result.hasErrors()) {
			String errmsg = "";
			for(ObjectError err : result.getAllErrors()){
			    errmsg = errmsg + err.toString() + "<br />";
			}
			m.addAttribute("errorFormMessage", "Nahrávání souboru se nezdařilo: <br />" + errmsg);
			return "upload/formUploadFile";
		}
		
		// include item to database
		String fileName = pomFileService.getUniqueFileName();
		//String fileName = Long.toString(id) + ".xml";
		
		// upload file
		try {
			uploadPOMFile.upload(uploadedFile, fileName);
			m.addAttribute("successFormMessage", "Nahrání souboru bylo úspěšné");
		} catch (IOException ex) {
			m.addAttribute("errorFormMessage", "Nahrávání souboru se nezdařilo: " + ex.getMessage());
			return "upload/formUploadFile";
		}
		
		// validation
		try {
			validator.validatePom(fileName, email);
		} catch (IOException ex) {
			Logger.getLogger(UploadController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "upload/formUploadFile";
	}
}