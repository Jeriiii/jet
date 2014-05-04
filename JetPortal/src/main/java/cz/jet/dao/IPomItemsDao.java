/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.dao;

import cz.jet.models.UploadedFile;
import cz.jet.services.exceptions.NotCreatedDirException;
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
	 * Starts new reading instance
	 *
	 * @param id identifer of result
	 * @return ticket (identifer) for this reading instance
	 */
	public int startNewReading(String id);

	/**
	 * @param ticket identifer of reading instance
	 * @return next line of reading instance or null if there is no next line
	 */
	public String getNextLine(int ticket);

	/**
	 * Closes instance and ends reading properly
	 *
	 * @param ticket identifer of reading instance to close
	 */
	public void endReading(int ticket);

}
