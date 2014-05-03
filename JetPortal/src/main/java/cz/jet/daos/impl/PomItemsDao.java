/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.daos.impl;

import cz.jet.dao.IPomItemsDao;
import cz.jet.models.UploadedFile;
import cz.jet.services.UploadPOMFileService;
import cz.jet.services.exceptions.NotCreatedDirException;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
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
	 * Get new unique file name for POM
	 *
	 * @return new file name
	 */
	private String getUniqueFileName() {
		String uuid = UUID.randomUUID().toString();
		String fileName;
		for (int i = 0;; i++) {
			fileName = "pom" + uuid + i;
			File f = new File(path + "poms/" + "working-" + fileName + ".xml");
			if (!f.exists()) {
				break;
			}
		}

		return fileName;
	}

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

}
