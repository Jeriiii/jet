/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.daos.impl;

import cz.jet.mappers.PomItemsMapper;
import cz.jet.models.PomItemEntite;
import cz.jet.dao.IPomItemsDao;
import cz.jet.models.POMFile;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author Petr Kukrál
 */
@Service
public class PomItemsDao extends BaseDao implements IPomItemsDao {
	
	@Value("${filePath}")
    public String path;
	
	@Value("${suffix}")
	public String suffix;
	
	@Value("${prefixWorking}")
	public String prefixWorking;
	
	@Value("${prefixFinish}")
	public String prefixFinish;
	
	@Override
	public String getUniqueFileName() {
		String uuid = UUID.randomUUID().toString();
		String fileName;
		for(int i = 0;; i++) {
			fileName = "pom" + uuid + i;
			File f = new File(path + prefixWorking + fileName + suffix);
			if(! f.exists()) {
				break;
			}
		}
		
		return fileName;
	}
	
//	@Override
//	public PomItemEntite getPomItem(int id) {
//		String SQL = "SELECT * FROM " + POM_ITEMS_TABLE + " WHERE id = ?";
//		PomItemEntite pitem = (PomItemEntite) this.getJdbcTemplateObject().queryForObject(SQL,
//				new Object[]{id}, new PomItemsMapper());
//		return pitem;
//	}
//
//	@Override
//	public long insertNewPomItem(String email) {
//		HashMap<String, String> items = getNewHashMap();
//		items.put("email", email);
//		return insert(POM_ITEMS_TABLE, items);
//	}
//	
//	@Override
//	public void updateResult(String result, Long itemID) {
//		HashMap<String, String> items = getNewHashMap();
//		items.put("result", result);
//		
//		HashMap<String, String> wheres = getNewHashMap();
//		wheres.put("id", itemID.toString());
//		
//		update(POM_ITEMS_TABLE, items, wheres);
//	}	

}