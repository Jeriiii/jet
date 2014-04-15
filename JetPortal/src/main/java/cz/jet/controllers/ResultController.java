/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.controllers;

import cz.jet.models.PomItemEntite;
import cz.jet.dao.IPomItemsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Josef Hula a Jan Kotalík
 */
@Controller
public class ResultController {
    
    @Autowired 
    private ApplicationContext context;
    
    @Autowired
    private IPomItemsDao pomItems;
    
    @Value("${resultPath}")
    private String resultPath;
	
    @Value("${prefixWorking}")
    private String prefixWorking;
	
    @Value("${prefixFinish}")
    private String prefixFinish;
    
    @RequestMapping(value="result", method=RequestMethod.GET)
    public String showResult(@RequestParam("id") String id, Model m) {		
         //m.addAttribute("resultNumber", id);
         //PomItemsService pomResultService = (PomItemsService) context.getBean("pomResultsService");
         //String result = pomResultService.getPomItem(id)
	//m.addAttribute("uploadedFile", new UploadedFile()); 
	m.addAttribute("filename", id + ".txt");
	
	m.addAttribute("workingPath", resultPath + prefixWorking + id + ".txt");
	m.addAttribute("finishPath", resultPath + prefixFinish + id + ".txt");
	
	return "result/result";
    }
    

}
