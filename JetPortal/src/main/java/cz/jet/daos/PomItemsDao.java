/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.daos;

import cz.jet.mappers.PomItemsMapper;
import cz.jet.models.PomItemEntite;
import cz.jet.services.PomItemsService;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Petr Kukrál
 */
public class PomItemsDao extends BaseDao implements PomItemsService {

	@Override
	public PomItemEntite getPomItem(int id) {
		String SQL = "SELECT * FROM " + POM_ITEMS_TABLE + " WHERE id = ?";
		PomItemEntite pitem = (PomItemEntite) this.getJdbcTemplateObject().queryForObject(SQL,
				new Object[]{id}, new PomItemsMapper());
		return pitem;
	}

	@Override
	public long insertNewPomItem(String email) {
		items = getNewHashMap();
		items.put("email", email);
		return insert(POM_ITEMS_TABLE, items);
	}

}
