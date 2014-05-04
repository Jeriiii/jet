/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.controllers;

import cz.jet.daos.impl.PomItemsDao;
import cz.jet.services.DeferredReadService;
import cz.jet.services.OutputTagService;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author Jan Kotalík
 */
@Controller
public class ResultController {

	/**
	 * Logger instance
	 */
	private static final Logger log = Logger.getLogger(ResultController.class.getName());

	/**
	 * time to wait if result is not avalible
	 */
	private static final long LONG_POLLING_TIMEOUT = 5000;//ms

	/**
	 * Service for long polling
	 */
	@Autowired
	private DeferredReadService updateService;

	/**
	 * Service for ouput formatting
	 */
	@Autowired
	private OutputTagService tagService;

	/**
	 * DAO for POM
	 */
	@Autowired
	private PomItemsDao pomDao;

	/**
	 * Initial call Returns finished results or subscribes new reading in
	 * DefferedReadService
	 *
	 * @param id identifer of result
	 * @param m viz Spring documentation
	 * @return result template
	 */
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

	/**
	 * Called by AJAX Returns next line of ticketed result or waits until
	 * timeout time out
	 *
	 * @param ticket read ticket for result identify and reading instance
	 * @return next line of readed result
	 */
	@RequestMapping("/result/update")
	@ResponseBody
	public DeferredResult<String> getUpdate(@RequestParam("ticket") int ticket) {
		final DeferredResult<String> result = new DeferredResult<String>(LONG_POLLING_TIMEOUT);
		updateService.getUpdate(ticket, result);
		return result;
	}

	/**
	 * Called by AJAX
	 *
	 * @param ticket read ticket for result identify and reading instance
	 * @param id identifer of result (need it for close)
	 * @return OK status with entire content of result or NOT FOUND status
	 */
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
	 * Tries to get content of file with finished results If result is found,
	 * makes some modifications
	 *
	 * @param id identifer of result
	 * @return content of file or null if file not exists
	 */
	private String tryGetFinishedResult(String id) {
		String content = pomDao.getFinishedResult(id);
		if (content != null) {
			content = tagService.addTagsToContent(content);
		}
		return content;
	}

}
