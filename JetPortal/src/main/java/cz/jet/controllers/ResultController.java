/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.controllers;

import cz.jet.services.DeferredFileReadService;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	private static final Logger log = Logger.getLogger(ResultController.class.getName());

	// time to wait if result is not avalible
	private static final long LONG_POLLING_TIMEOUT = 2500;//ms

	/**
	 * Functional example for using blocking queue for long polling part 1 / 3
	 * http://java.dzone.com/articles/long-polling-spring-32s
	 */
	@Autowired
	private DeferredFileReadService updateService;

	@Value("${filesPath}")
	private String path;

	private synchronized String getFilePath(String prefix, String id) {
		return path + "results/" + prefix + "-" + id + ".txt";
	}

	/*
	 * Functional example for using blocking queue for long polling part 2 / 3
	 * http://java.dzone.com/articles/long-polling-spring-32s
	 *
	 */
	@RequestMapping("/deferred")
	@ResponseBody
	public DeferredResult<String> getUpdate(@RequestParam("ticket") int ticket) {
		final DeferredResult<String> result = new DeferredResult<String>(LONG_POLLING_TIMEOUT);
		updateService.getUpdate(ticket, result);
		return result;
	}

	/**
	 *
	 * @param lastmod
	 * @param id
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "result/update", method = RequestMethod.GET)
	public String updateResult(@RequestParam("id") String id, @RequestParam("lastmod") long lastmod, Model m) {
//	int waitings = 0;
//	String content = "";
//	content = tryGetFinishedResult(id);
//	if(content != null){
//	    m.addAttribute("fincontent", content);
//	}else{
//	    while(true){
//		File working = new File(getFilePath(WORKING_PREFIX, id));
//		if(working.exists() && working.canRead()){
//		    if(lastmod != working.lastModified()){
//			Scanner scan = null;
//			try {
//			    scan = new Scanner(working).useDelimiter("\\Z"); //delimiter on end of input
//			    if(scan.hasNext()){
//				content = scan.next();
//				lastmod = working.lastModified();
//				break;
//			    }
//			} catch (FileNotFoundException ex) {
//			    log.log(Level.SEVERE, null, ex);
//			    m.addAttribute("error", "Could not scan.");
//			}finally{
//			    if(scan != null){
//				scan.close();
//			    }
//			}
//		    }
//		}else{
//		    m.addAttribute("error", "File was not found or is not readable.");
//		    break;
//		}
//		//if nothing (no break), wait
////		if(waitings >= MAX_WAITINGS){
////		    break;
////		}
//		waitings++;
//		try {
//		    Thread.sleep(LONG_POLLING_TIMEOUT);
//		} catch (InterruptedException ex) {
//		    log.log(Level.SEVERE, null, ex);
//		}
//	    }
//	    content = modifyContent(content);
//	    m.addAttribute("content", content);
//	    m.addAttribute("lastmod", lastmod);
//	}
		return "result/update";
	}

	/**
	 * Tries to get content of file with finished results
	 *
	 * @param id file id
	 * @return content of file or null if file not exists
	 */
	private synchronized String tryGetFinishedResult(String id) {
		File finish = new File(getFilePath(FINISH_PREFIX, id));
		String content = "";
		if (finish.exists()) {
			Scanner scan = null;
			try {
				scan = new Scanner(finish).useDelimiter("\\Z"); //delimiter na konec souboru
				if (scan.hasNext()) {
					content = scan.next();
				}
			} catch (FileNotFoundException ex) {
				log.log(Level.SEVERE, null, ex);
				return null;
			} finally {
				if (scan != null) {
					scan.close();
				}
			}
		} else {
			return null;
		}
		content = modifyContent(content);
		return content;
	}

	@RequestMapping(value = "result", method = RequestMethod.GET)
	public String showResult(@RequestParam("id") String id, Model m) {
		m.addAttribute("fileid", id);
		String content = tryGetFinishedResult(id);
		if (content != null) {
			m.addAttribute("fincontent", content);

		} /**
		 * Functional example for using blocking queue for long polling part 3 /
		 * 3 http://java.dzone.com/articles/long-polling-spring-32s
		 */
		else {
			int ticket = updateService.subscribe(new File(getFilePath(WORKING_PREFIX, id)));
			m.addAttribute("ticket", ticket);
		}

		return "result/result";
	}

	private synchronized String modifyContent(String content) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		return content;
	}

	/**
	 * Add HTML tag to string
	 *
	 * @param line Line of result
	 * @return Line of result with html tags
	 */
	private String addHtmlTags(String line) {
		//types
		String info = "[INFO]";
		String error = "[ERROR]";
		String warning = "[WARNING]";

		if (line.toLowerCase().contains(info)) {
			line.replace(info, "<span class='info'>" + info + "</span>");
			line = "<tr class=\"info\"><td>" + line + "</td></tr>";
		} else if (line.toLowerCase().contains(error)) {
			line.replace(info, "<span class='error'>" + error + "</span>");
			line = "<tr class=\"danger\"><td>" + line + "</td></tr>";
		} else if (line.toLowerCase().contains(warning)) {
			line.replace(info, "<span class='warning'>" + warning + "</span>");
			line = "<tr class=\"warning\"><td>" + line + "</td></tr>";
		}

		//line
		String strLine = "----*";//Str.matches
		if (line.matches(strLine)) {
			line.replaceAll(strLine, "<hr />");
		}

		return line;
	}

}
