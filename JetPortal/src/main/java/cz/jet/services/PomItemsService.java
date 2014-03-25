/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.services;


import cz.jet.models.PomItemEntite;

/**
 *
 * @author Petr Kukrál
 */
public interface PomItemsService extends BaseService {
    
    public PomItemEntite getPomItem(int id);
	
	public long insertNewPomItem(String email);
	
}
