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
 * @author Petr Kukrál a Jan Kotalík
 */
@Repository
public class PomItemsDao implements IPomItemsDao {

	/**
	 * prefix for file with completed result
	 */
	private static final String FINISH_PREFIX = "finish";

	/**
	 * prefix for file which is still updated
	 */
	private static final String WORKING_PREFIX = "working";

	private static final Logger log = Logger.getLogger(PomItemsDao.class.getName());

	/**
	 * List of reading instances
	 */
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

	/**
	 * Tries get finished result (if is validation finished)
	 *
	 * @param id identifer of result
	 * @return content of result or null if finished result does not exist
	 */
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
				log.log(Level.SEVERE, "an exception was thrown", ex);
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

	/**
	 * Test existence of finished result
	 *
	 * @param id identifer of result
	 * @return if finished result does or does not exist
	 */
	@Override
	public boolean isResultFinished(String id) {
		File finish = new File(getFilePath(FINISH_PREFIX, id));
		return finish.exists();
	}

	/**
	 * Starts new reading instance
	 *
	 * @param id identifer of result
	 * @return ticket (identifer) for this reading instance
	 */
	@Override
	public int startNewReading(String id) {
		try {
			File file = new File(getFilePath(WORKING_PREFIX, id));
			int ticket = getNewTicket(this.scanners);
			RandomAccessFile scan = new RandomAccessFile(file, "r");
			this.scanners.put((Integer) ticket, scan);
			return ticket;
		} catch (FileNotFoundException ex) {
			log.log(Level.SEVERE, "an exception was thrown", ex);
			return -1;
		}
	}

	/**
	 * @param ticket identifer of reading instance
	 * @return all avalible next lines in file
	 */
	@Override
	public String getAllNextLines(int ticket) {
		RandomAccessFile scan = scanners.get((Integer) ticket);
		String newline = System.getProperty("line.separator");
		String content = "";
		String temp;
		try {
			temp = scan.readLine();
			while (temp != null) {
				content = content + temp + newline;
				temp = scan.readLine();
			}

		} catch (IOException ex) {
			log.log(Level.SEVERE, "an exception was thrown", ex);
			return null;
		}
		if (!content.isEmpty()) {
			return content;
		} else {
			return null;
		}
	}

	/**
	 * @param ticket identifer of reading instance
	 * @return next line of reading instance or null if there is no next line
	 */
	@Override
	public String getNextLine(int ticket) {
		RandomAccessFile scan = scanners.get((Integer) ticket);
		try {
			return scan.readLine();
		} catch (IOException ex) {
			log.log(Level.SEVERE, "an exception was thrown", ex);
			return null;
		}
	}

	/**
	 * Closes instance and ends reading properly
	 *
	 * @param ticket identifer of reading instance to close
	 */
	@Override
	public void endReading(int ticket) {
		RandomAccessFile scan = scanners.get((Integer) ticket);
		try {
			scan.close();
		} catch (IOException ex) {
			log.log(Level.SEVERE, "an exception was thrown", ex);
		}
		scanners.remove((Integer) ticket);
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

	/**
	 * Build entire filepath
	 *
	 * @param prefix prefix of file
	 * @param id identifer of file
	 * @return file path
	 */
	private synchronized String getFilePath(String prefix, String id) {
		return path + "results/" + prefix + "-" + id + ".txt";
	}

	/**
	 * Generates new ticket
	 *
	 * @param uniquefor ticket has to be unique for this list
	 * @return unsigned number which can be used as key for list
	 */
	private int getNewTicket(HashMap uniquefor) {
		Random rand = new Random();
		int ticket = rand.nextInt(Integer.MAX_VALUE);
		while (uniquefor.containsKey((Integer) ticket)) {
			ticket = rand.nextInt(Integer.MAX_VALUE);
		}
		return ticket;
	}

}
