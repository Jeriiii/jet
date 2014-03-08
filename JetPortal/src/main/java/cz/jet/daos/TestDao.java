/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.daos;

import javax.sql.DataSource;
import cz.jet.mappers.TestMapper;
import cz.jet.models.TestObject;
import org.springframework.jdbc.core.JdbcTemplate;
import cz.jet.services.TestService;

/**
 *
 * @author Honza
 */
public class TestDao implements TestService{

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    
    @Override
    public TestObject getTest(int id) {
	String SQL = "select * from Test where id = ?";
	TestObject test = jdbcTemplateObject.queryForObject(SQL, 
                        new Object[]{id}, new TestMapper());
      return test;
    }

    @Override
    public void setDataSource(DataSource dataSource) {
	this.dataSource = dataSource;
	this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    
}
