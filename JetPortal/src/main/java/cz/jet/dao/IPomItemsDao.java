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
 * @author Petr Kukrál
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

}
