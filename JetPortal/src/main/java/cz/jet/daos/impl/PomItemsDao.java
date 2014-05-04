/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.daos.impl;

import cz.jet.controllers.ResultController;
import cz.jet.dao.IPomItemsDao;
import cz.jet.models.UploadedFile;
import cz.jet.services.DeferredReadService;
import cz.jet.services.UploadPOMFileService;
import cz.jet.services.exceptions.NotCreatedDirException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Dao for POM file
 *
 * @author Petr Kukrál
 */
@Repository
public class PomItemsDao implements IPomItemsDao {

	private static final String FINISH_PREFIX = "finish";
	private static final String WORKING_PREFIX = "working";

	private static final Logger log = Logger.getLogger(ResultController.class.getName());

	private final HashMap<Integer, RandomAccessFile> scanners = new HashMap<Integer, RandomAccessFile>();

	/**
	 * path where to store file, set in config.properties
	 */
	@Value("${filesPath}")
	public String path;
	/**
	 * Service for upload file
	 */
	@Autowired
	private UploadPOMFileService uploadPOMFile;

	/**
	 * Save POM file
	 *
	 * @param uploadedFile POM file
	 * @return New generated file name
	 * @throws IOException
	 * @throws NotCreatedDirException Directory wasn't created
	 */
	@Override
	public String save(UploadedFile uploadedFile) throws IOException, NotCreatedDirException {
		String fileName = this.getUniqueFileName();

		uploadPOMFile.upload(uploadedFile, fileName);

		return fileName;
	}

	@Override
	public String getFinishedResult(String id) {
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
		return content;
	}

	@Override
	public int startNewReading(String id) {
		try {
			File file = new File(getFilePath(WORKING_PREFIX, id));
			int ticket = getNewTicket(this.scanners);
			RandomAccessFile scan = new RandomAccessFile(file, "r");
			this.scanners.put((Integer) ticket, scan);
			return ticket;
		} catch (FileNotFoundException ex) {
			Logger.getLogger(PomItemsDao.class.getName()).log(Level.SEVERE, null, ex);
			return -1;
		}
	}

	@Override
	public String getNextLine(int ticket) {
		RandomAccessFile scan = scanners.get((Integer) ticket);
		try {
			return scan.readLine();
		} catch (IOException ex) {
			Logger.getLogger(PomItemsDao.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	/**
	 * Get new unique file name for POM
	 *
	 * @return new file name
	 */
	private String getUniqueFileName() {
		String uuid = UUID.randomUUID().toString();
		String fileName;
		for (int i = 0;; i++) {
			fileName = "pom" + uuid + i;
			File f = new File(path + "poms/" + WORKING_PREFIX + "-" + fileName + ".xml");
			if (!f.exists()) {
				break;
			}
		}

		return fileName;
	}

	private synchronized String getFilePath(String prefix, String id) {
		return path + "results/" + prefix + "-" + id + ".txt";
	}

	private int getNewTicket(HashMap uniquefor) {
		Random rand = new Random();
		int ticket = rand.nextInt(Integer.MAX_VALUE);
		while (uniquefor.containsKey((Integer) ticket)) {
			ticket = rand.nextInt(Integer.MAX_VALUE);
		}
		return ticket;
	}

	@Override
	public void endReading(int ticket) {
		RandomAccessFile scan = scanners.get((Integer) ticket);
		try {
			scan.close();
		} catch (IOException ex) {
			Logger.getLogger(DeferredReadService.class.getName()).log(Level.SEVERE, null, ex);
		}
		scanners.remove((Integer) ticket);
	}
}
