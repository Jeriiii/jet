package cz.jet.controllers;

import cz.jet.models.Subscriber;
import cz.jet.models.Subscriber.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author Petr Kukrál
 */
 
@Controller//anotace kontroleru
public class UploadController {//nazev tridy musi byt stejny jako url!!!
    
    @Autowired 
    private ApplicationContext context;
    //////////////////////////////
	
	// spustí se ještě před metodou loadFormPage
	// přidá se k modelu pro kontajner na selectbox Frequency
	// alternativně by to šlo přepsat rovnou do metody loadFormPage takto: m.addAttribute("frequencies", Frequency.values())
	@ModelAttribute("frequencies")
	public Frequency[] frequencies() {
		return Frequency.values();
	}

	// render pro jsp s formulářem
	@RequestMapping(value="form", method=RequestMethod.GET)
	public String loadFormPage(Model m) {
		// objekt, do kterého se vloží data z formuláře
		Subscriber subscriber = new Subscriber();
		// defaultní nastavení
		subscriber.setReceiveNewsletter(true);
		subscriber.setNewsletterFrequency(Frequency.HOURLY);
		
		m.addAttribute("subscriber", new Subscriber());
		return "upload/form";
	}

	@RequestMapping(value="form", method=RequestMethod.POST)
	public String submitForm(Subscriber subscriber, Model m) {
		m.addAttribute("message", "Successfully saved person: " + subscriber.toString());
		return "upload/form";
	}
	
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