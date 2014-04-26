/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.controllers;

import cz.jet.services.DeferredResultService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

/**
 *
 * @author Josef Hula a Jan Kotalík
 */
@Controller
public class ResultController {
    
    private static final String FINISH_PREFIX = "finish";
    private static final String WORKING_PREFIX = "working";
    
//    @Autowired
//    private DeferredResultService updateService;
    
    @Value("${filesPath}")
    private String path;
    
    private synchronized String getFilePath(String prefix, String id){
	return path + "results/" + prefix + "-" + id + ".txt";
    }
    
    
//    @RequestMapping("/result/begin")
//    @ResponseBody
//    public String start() {
//	updateService.subscribe();
//	return "OK";
//    }
//
//    @RequestMapping("/result/deferred")
//    @ResponseBody
//    public DeferredResult<Message> getUpdate() {
//	final DeferredResult<Message> result = new DeferredResult<Message>();
//	updateService.getUpdate(result);
//	return result;
//    }
    
    
    //startupdating
    
    @RequestMapping(value="result/update", method=RequestMethod.GET)
    public String updateResult(@RequestParam("id") String id, Model m) {
	
	String content = tryGetFinishedResult(id);
	if(content != null){
	    m.addAttribute("fincontent", content);
	}else{
	    content = "";
	    File working = new File(getFilePath(WORKING_PREFIX, id));
	    if(working.exists() && working.canRead()){
		Scanner scan = null;
		try {
		    scan = new Scanner(working).useDelimiter("\\Z"); //delimiter na konec souboru
		    if(scan.hasNext()){
			content = scan.next();
		    }
		} catch (FileNotFoundException ex) {
		    Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
		    m.addAttribute("error", "Could not scan.");
		}finally{
		    if(scan != null){
			scan.close();
		    }
		}
		m.addAttribute("content", content);
		
	    }else{
		m.addAttribute("error", "File was not found or is not readable.");
	    }
	}
	return "result/update";
	
    }
    /**
     * Tries to get content of file with finished results
     * @param id  file id
     * @return content of file or null if file not exists
     */
    private synchronized String tryGetFinishedResult(String id){
	File finish = new File(getFilePath(FINISH_PREFIX, id));
		String content = "";
		if(finish.exists()){
		    Scanner scan = null;
		    try {
			scan = new Scanner(finish).useDelimiter("\\Z"); //delimiter na konec souboru
			if(scan.hasNext()){
			    content = scan.next();
			}
		    } catch (FileNotFoundException ex) {
			Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		    }finally{
			if(scan != null){
			    scan.close();
			}
		    }
		}else{
		    return null;
		}
		content = modifyFinishedContent(content);
		return content;
    }
    
    @RequestMapping(value="result", method=RequestMethod.GET)
    public String showResult(@RequestParam("id") String id, Model m) {
		m.addAttribute("fileid", id);
		String content = tryGetFinishedResult(id);
		if(content != null){
		    m.addAttribute("fincontent", content);
		}
	return "result/result";
    }

    private synchronized String modifyFinishedContent(String content) {
	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	return content;
    }
    
    
    

}
