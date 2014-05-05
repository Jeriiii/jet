package cz.jet.services;

import cz.jet.daos.impl.PomItemsDao;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class DeferredReadService {

	private static final Logger logger = Logger.getLogger(DeferredReadService.class.getName());

	/**
	 * how long service waits if has nothing to read and then try read again
	 */
	public static final int TRY_READ_AGAIN_IN = 500;

	/**
	 * DAO for POM
	 */
	@Autowired
	private PomItemsDao pomDao;

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
	 * @param ticket identifer of reading instance
	 * @param result
	 */
	@Async
	public void getUpdate(int ticket, DeferredResult<String> result) {
		try {
			while (!result.isSetOrExpired()) {
				String content = pomDao.getAllNextLines(ticket);
				if (content != null) {
					content = tagService.addTagsToContent(content);
					result.setResult(content);
				} else {
					Thread.sleep(TRY_READ_AGAIN_IN);
				}
			}
		} catch (InterruptedException ex) {
			logger.log(Level.SEVERE, null, ex);
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
