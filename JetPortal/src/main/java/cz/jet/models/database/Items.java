/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.models.database;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Items {
	
	List<Item> items;

	public Items() {
		items = new LinkedList<Item>();
	}
	
	public void addItem(Item i) {
		items.add(i);
	}

	public List<Item> getItems() {
		return items;
	}
	
}
