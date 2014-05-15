/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.jamp.dao;

import cz.zcu.kiv.jamp.models.UploadedFile;
import cz.zcu.kiv.jamp.services.exceptions.NotCreatedDirException;
import java.io.IOException;

/**
 *
 * @author Petr Kukrál a Jan Kotalík
 */
public interface IPomItemsDao {

	/**
	 * Save POM file
	 *
	 * @param uploadedFile POM file
	 * @return New generated file name
	 * @throws IOException
	 * @throws NotCreatedDirException Directory wasn't created
	 */
	public String save(UploadedFile uploadedFile) throws IOException, NotCreatedDirException;

	/**
	 * Tries get finished result (if is validation finished)
	 *
	 * @param id identifer of result
	 * @return content of result or null if finished result does not exist
	 */
	public String getFinishedResult(String id);

	/**
	 * Test existence of finished result
	 *
	 * @param id identifer of result
	 * @return if finished result does or does not exist
	 */
	public boolean isResultFinished(String id);

	/**
	 * Starts new reading instance
	 *
	 * @param id identifer of result
	 * @return ticket (identifer) for this reading instance
	 */
	public int startNewReading(String id);


	/**
	 * @param ticket identifer of reading instance
	 * @return all avalible next lines in file
	 */
	public String getAllNextLines(int ticket);

	/**
	 * Closes instance and ends reading properly
	 *
	 * @param ticket identifer of reading instance to close
	 */
	public void endReading(int ticket);

}
