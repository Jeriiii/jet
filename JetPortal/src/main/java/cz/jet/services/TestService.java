/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.services;


import javax.sql.DataSource;
import cz.jet.models.TestObject;

/**
 *
 * @author Honza
 */
public interface TestService {
    
    public TestObject getTest(int id);
    
    public void setDataSource(DataSource ds);
}
