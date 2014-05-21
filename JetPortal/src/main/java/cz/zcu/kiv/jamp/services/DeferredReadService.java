package cz.zcu.kiv.jamp.services;

import cz.zcu.kiv.jamp.dao.IPomItemsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DeferredReadService {

	private static final Logger log = Logger.getLogger(DeferredReadService.class.getName());

	/**
	 * how long service waits if has nothing to read and then try read again
	 */
	public static final int TRY_READ_AGAIN_IN = 500;

	/**
	 * Send this symbol to deffered result when finds out reading is complete
	 */
	public static final String END_SYMBOL = "123end987";//please do not use javascript syntax symbols

	/**
	 * result which is send when timeout times out
	 */
	public static final String TIMEOUT_RESULT = "123nodata456";

	/**
	 * DAO for POM
	 */
	@Autowired
	private IPomItemsDao pomDao;

	/**
	 * Ouput modify service
	 */
	@Autowired
	private OutputTagService tagService;

	/**
	 * Creating new reading instance
	 *
	 * @param id identifer of result
	 * @return ticket for acessing created reading instance
	 */
	public int subscribe(String id) { //has to return ticket
		return pomDao.startNewReading(id);
	}

	/**
	 * This method will set result of Deferred result if there is a next line in
	 * reading instance. Otherwise it keeps trying again after some time until
	 * DeferredResult time out
	 *
	 *
	 * WARNING: WHEN RESULT EXPIRES BEFORE SET, IT WILL STUCK. BUXFIX NECESSARY
	 *
	 * @param ticket identifer of reading instance
	 * @param id identifer of reading instance
	 */
	@Async
	public void getUpdate(int ticket, String id, DeferredResult<String> result) {
		try {
			while (!result.isSetOrExpired()) {
				synchronized (result) {
					String content = pomDao.getAllNextLines(ticket);
					if (content != null) {
						if (content.contains(END_SYMBOL)) {
							endScan(ticket);
						}
						content = tagService.addTagsToContent(content);
						result.setResult(content);
						return;
					}
				}
				Thread.sleep(TRY_READ_AGAIN_IN);
			}
		} catch (InterruptedException ex) {
			log.log(Level.SEVERE, "an exception was thrown", ex);
		}
	}

	/**
	 * Destroys reading instance properly
	 *
	 * @param ticket identifer of instance
	 */
	public void endScan(int ticket) {
		pomDao.endReading(ticket);
	}

}
