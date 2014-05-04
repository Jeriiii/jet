/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.controllers;

import cz.jet.daos.impl.PomItemsDao;
import cz.jet.services.DeferredReadService;
import cz.jet.services.OutputTagService;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	private static final Logger log = Logger.getLogger(ResultController.class.getName());

	// time to wait if result is not avalible
	private static final long LONG_POLLING_TIMEOUT = 5000;//ms

	/**
	 * Functional example for using blocking queue for long polling part 1 / 3
	 * http://java.dzone.com/articles/long-polling-spring-32s
	 */
	@Autowired
	private DeferredReadService updateService;

	@Autowired
	private OutputTagService tagService;

	/**
	 * DAO for POM
	 */
	@Autowired
	private PomItemsDao pomDao;

	/*
	 * Functional example for using blocking queue for long polling part 2 / 3
	 * http://java.dzone.com/articles/long-polling-spring-32s
	 *
	 */
	@RequestMapping("/result/update")
	@ResponseBody
	public DeferredResult<String> getUpdate(@RequestParam("ticket") int ticket) {
		final DeferredResult<String> result = new DeferredResult<String>(LONG_POLLING_TIMEOUT);
		updateService.getUpdate(ticket, result);
		return result;
	}

	@RequestMapping("/result/finished")
	@ResponseBody
	public ResponseEntity<String> getFinishedContent(@RequestParam("ticket") int ticket, @RequestParam("id") String id) {
		String content = tryGetFinishedResult(id);
		if (content != null) {//file exists
			updateService.endScan(ticket);
			return new ResponseEntity<String>(content, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Tries to get content of file with finished results
	 *
	 * @param id file id
	 * @return content of file or null if file not exists
	 */
	private String tryGetFinishedResult(String id) {
		String content = pomDao.getFinishedResult(id);
		if (content != null) {
			content = modifyContent(content);
		}
		return content;
	}

	@RequestMapping(value = "result", method = RequestMethod.GET)
	public String showResult(@RequestParam("id") String id, Model m) {
		m.addAttribute("fileid", id);
		String content = tryGetFinishedResult(id);
		if (content != null) {
			m.addAttribute("fincontent", content);
		} else {
			int ticket = updateService.subscribe(id);
			m.addAttribute("ticket", ticket);
		}
		return "result/result";
	}

	private String modifyContent(String content) {
		content = tagService.addTagsToContent(content);
		return content;
	}

}
