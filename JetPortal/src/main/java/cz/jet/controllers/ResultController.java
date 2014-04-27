/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
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
    
    private static final String FINISH_PREFIX = "finish";
    private static final String WORKING_PREFIX = "working";
    
    // time to wait if result is not avalible
    private static final long LONG_POLLING_TIMEOUT = 2500;//ms
    // maximum count of waitings for result (max number of try again)
    private static final int MAX_WAITINGS = 4;
    
    
    /**
     * Functional example for using blocking queue for long polling part 1 / 3
     * http://java.dzone.com/articles/long-polling-spring-32s
     */
//    @Autowired
//    private DeferredResultService updateService;
    
    @Value("${filesPath}")
    private String path;
    
    private synchronized String getFilePath(String prefix, String id){
	return path + "results/" + prefix + "-" + id + ".txt";
    }   
    
   /*
     * Functional example for using blocking queue for long polling part 2 / 3
     * http://java.dzone.com/articles/long-polling-spring-32s
     * 
     */
//    @RequestMapping("/deferred")
//    @ResponseBody
//    public DeferredResult<String> getUpdate() {
//
//	final DeferredResult<String> result = new DeferredResult<String>(5000);
////	
////	result.setResult("<strong>test</strong>");
//	updateService.getUpdate(result);
//	return result;
//    }
    /**
     * 
     * @param lastmod
     * @param id
     * @param m
     * @return 
     */
    @RequestMapping(value="result/update", method=RequestMethod.GET)
    public String updateResult(@RequestParam("id") String id, @RequestParam("lastmod") long lastmod, Model m) {
	int waitings = 0;
	String content = "";
	content = tryGetFinishedResult(id);
	if(content != null){
	    m.addAttribute("fincontent", content);
	}else{
	    while(true){
		File working = new File(getFilePath(WORKING_PREFIX, id));
		if(working.exists() && working.canRead()){
		    if(lastmod != working.lastModified()){
			Scanner scan = null;
			try {
			    scan = new Scanner(working).useDelimiter("\\Z"); //delimiter on end of input
			    if(scan.hasNext()){
				content = scan.next();
				lastmod = working.lastModified();
				break;
			    }
			} catch (FileNotFoundException ex) {
			    Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
			    m.addAttribute("error", "Could not scan.");
			}finally{
			    if(scan != null){
				scan.close();
			    }
			}
		    }
		}else{
		    m.addAttribute("error", "File was not found or is not readable.");
		    break;
		}
		//if nothing (no break), wait
		if(waitings >= MAX_WAITINGS){
		    break;
		}
		waitings++;
		try {
		    Thread.sleep(LONG_POLLING_TIMEOUT);
		} catch (InterruptedException ex) {
		    Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	    content = modifyContent(content);
	    m.addAttribute("content", content);
	    m.addAttribute("lastmod", lastmod);
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
		content = modifyContent(content);
		return content;
    }
    
    @RequestMapping(value="result", method=RequestMethod.GET)
    public String showResult(@RequestParam("id") String id, Model m) {
		m.addAttribute("fileid", id);
		String content = tryGetFinishedResult(id);
		if(content != null){
		    m.addAttribute("fincontent", content);
		}
		/**
		* Functional example for using blocking queue for long polling part 3 / 3
		* http://java.dzone.com/articles/long-polling-spring-32s
		*/
//		else{
//		    updateService.subscribe();
//		}
		
	return "result/result";
    }

    private synchronized String modifyContent(String content) {
	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	return content;
    }
    
    
    

}
