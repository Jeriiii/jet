package cz.jet.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class DeferredFileReadService {

	private static final Logger logger = Logger.getLogger(DeferredFileReadService.class.getName());

	public static final int TRY_READ_AGAIN_IN = 500;//how long service waits if has nothing to read and then try read again

	private final HashMap<Integer, RandomAccessFile> scanners = new HashMap<Integer, RandomAccessFile>();

	public int subscribe(File file) { //has to return ticket
		try {
			int ticket = getNewTicket(this.scanners);
			RandomAccessFile scan = new RandomAccessFile(file, "r");
			this.scanners.put((Integer) ticket, scan);
			logger.log(Level.INFO, "Creating new scanner with ticket {0}", ticket);
			return ticket;
		} catch (FileNotFoundException ex) {
			Logger.getLogger(DeferredFileReadService.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	private int getNewTicket(HashMap uniquefor) {
		Random rand = new Random();
		int ticket = rand.nextInt(Integer.MAX_VALUE);
		while (uniquefor.containsKey((Integer) ticket)) {
			ticket = rand.nextInt(Integer.MAX_VALUE);
		}
		return ticket;
	}

	@Async
	public void getUpdate(int ticket, DeferredResult<String> result) {
		try {
			RandomAccessFile scan = scanners.get((Integer) ticket);
			while (!result.isSetOrExpired()) {
				String line = scan.readLine();
				if (line != null) {
					System.out.println(line);
					line = addHtmlTags(line);
					System.out.println(line);
					result.setResult(line);
				} else {
					Thread.sleep(TRY_READ_AGAIN_IN);
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(DeferredFileReadService.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(DeferredFileReadService.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void closeScan(int ticket) {
		RandomAccessFile scan = scanners.get((Integer) ticket);
		try {
			scan.close();
		} catch (IOException ex) {
			Logger.getLogger(DeferredFileReadService.class.getName()).log(Level.SEVERE, null, ex);
		}
		scanners.remove((Integer) ticket);
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

		if (line.contains(info)) {
			line = line.replace(info, "<td><span class='info'>" + info + "</span></td><td>");
			line = "<tr class=\"info\">" + line + "</td></tr>";
		} else if (line.contains(error)) {
			line = line.replace(error, "<td><span class='error'>" + error + "</span></td><td>");
			line = "<tr class=\"danger\">" + line + "</td></tr>";
		} else if (line.contains(warning)) {
			line = line.replace(warning, "<td><span class='warning'>" + warning + "</span></td><td>");
			line = "<tr class=\"warning\">" + line + "</td></tr>";
		}

		//line
		String strLine = "----*";//Str.matches
		if (line.matches(strLine)) {
			line.replaceAll(strLine, "<hr />");
		}
		System.out.println(line);
		return line;
	}

}