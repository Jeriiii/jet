/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.services;


import cz.jet.models.PomItemEntite;
import javax.sql.DataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Petr Kukrál
 */

public interface PomItemsService  {
    
	public void setDataSource(DataSource dataSource);
	
    public PomItemEntite getPomItem(int id);
	
    public long insertNewPomItem(String email);
	
	public void updateResult(String result, Long itemID);
	
}
