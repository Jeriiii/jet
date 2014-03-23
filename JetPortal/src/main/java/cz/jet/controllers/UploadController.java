package cz.jet.controllers;

import cz.jet.models.Subscriber;
import cz.jet.models.Subscriber.Frequency;
import cz.jet.models.UploadedFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Petr Kukrál
 */
 
// podle příkladu http://www.javacodegeeks.com/2013/04/spring-mvc-form-tutorial.html
@Controller//anotace kontroleru
public class UploadController {//nazev tridy musi byt stejny jako url!!!
    
    @Autowired 
    private ApplicationContext context;
    //////////////////////////////
	
//	@RequestMapping("/fileUploadForm")
//	public ModelAndView getUploadForm(
//			@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
//			BindingResult result) {
//		return new ModelAndView("uploadForm");
//	}
	
	// render pro jsp s formulářem
	@RequestMapping(value="form-upload-file", method=RequestMethod.GET)
	public String loadFormPage(Model m) {		
		m.addAttribute("uploadedFile", new UploadedFile());
		return "upload/formUploadFile";
	}

	@RequestMapping(value="form-upload-file", method=RequestMethod.POST)
	public ModelAndView fileUploaded(
			UploadedFile uploadedFile, BindingResult result) {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		MultipartFile file = uploadedFile.getFile();
//		fileValidator.validate(uploadedFile, result);

		String fileName = file.getOriginalFilename();

		if (result.hasErrors()) {
			return new ModelAndView("uploadForm");
		}

		try {
			inputStream = file.getInputStream();

			// cesta kam se ma soubor ulozit
			String path = "C:/webhostJava/files/";
			File newFile = new File(path + fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}

		return new ModelAndView("upload/formUploadFile", "message", fileName);
	}
	
//	// spustí se ještě před metodou loadFormPage
//	// přidá se k modelu pro kontajner na selectbox Frequency
//	// alternativně by to šlo přepsat rovnou do metody loadFormPage takto: m.addAttribute("frequencies", Frequency.values())
//	@ModelAttribute("frequencies")
//	public Frequency[] frequencies() {
//		return Frequency.values();
//	}
//
//	// render pro jsp s formulářem
//	@RequestMapping(value="form", method=RequestMethod.GET)
//	public String loadFormPage(Model m) {
//		// objekt, do kterého se vloží data z formuláře
//		Subscriber subscriber = new Subscriber();
//		// defaultní nastavení
//		subscriber.setReceiveNewsletter(true);
//		subscriber.setNewsletterFrequency(Frequency.HOURLY);
//		
//		m.addAttribute("subscriber", new Subscriber());
//		return "upload/form";
//	}
//
//	@RequestMapping(value="form", method=RequestMethod.POST)
//	public String submitForm(Subscriber subscriber, Model m) throws UnsupportedEncodingException {
//		
//		String file = "";//subscriber.getFile().getOriginalFilename();
//		m.addAttribute("message", "Successfully saved person: " + subscriber.toString() + file);
//		return "upload/form";
//	}
	
	//////////////////////////////
    /*
	Priklad fungovani frameworku
    podle tutorialu http://www.tutorialspoint.com/spring/spring_web_mvc_framework.htm
    */
    // url (value=...)se nemusi psat pokazde, lze jej napsat i primo pro celou tridu, viz. tutorial 
//   @RequestMapping(value = "/upload/form", method = RequestMethod.GET) 
//   public String printUpload(ModelMap model) {//promenne do viewu
//      model.addAttribute("message", "Ahoj, tady se nahrávají soubory.");//promenna message predana do jspcka
//      return "upload/form";// jmeno jspcka, v tomhle pripade se bude hledat hello.jsp (sestavuje se ve view Resolveru - viz dispatcher-servlet.xml)
//   }
    
}