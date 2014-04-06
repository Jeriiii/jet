/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.daos;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import com.google.common.base.Joiner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Petr Kukrál
 */
public class BaseDao {

	protected HashMap<String, String> items;
	protected HashMap<String, String> wheres;
	@Autowired 
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject = null;
	
	//TABLES
	public static final String POM_ITEMS_TABLE = "pom_items";
    public static final String POM_RESULTS_TABLE = "pom_results";
		
	protected JdbcTemplate getJdbcTemplateObject() {
		
		if(this.jdbcTemplateObject == null) {
			this.jdbcTemplateObject = new JdbcTemplate(dataSource);
		}
		
		return this.jdbcTemplateObject;
	}
	
	protected HashMap<String, String> getNewHashMap() {
		return new HashMap<String, String>();
	}
	
	protected long insert(String table, HashMap<String, String> items) {		
		List names = new LinkedList<String>();
		List questions = new LinkedList<String>();

		for (String key : items.keySet()) {
			names.add(key);
			questions.add("?");
		}	

		String namesStr = Joiner.on(",").join(names);
		String questonsStr = Joiner.on(",").join(questions);
		
		String SQL = "INSERT INTO " + table + " (" + namesStr + ") VALUES (" + questonsStr + ");";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		this.getJdbcTemplateObject().update(
			psc(SQL, items), 
			keyHolder
		);
		
		return keyHolder.getKey().longValue();
	}
	
	protected long update(String table, HashMap<String, String> items) {
		return update(table, items, null);
	}
	
	protected long update(String table, HashMap<String, String> items, HashMap<String, String> whereMap) {		
		// name = 'value'
		List assignments = new LinkedList<String>();

		for (String key : items.keySet()) {
			assignments.add(key + "=?" );
		}
		
		String assignmentsStr = Joiner.on(",").join(assignments);	
		String where = this.getWhereSQLFromMap(whereMap);
		
		String SQL = "UPDATE " + table + " SET " + assignmentsStr  + " " + where + ";";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		this.getJdbcTemplateObject().update(
			psc(SQL, items), 
			keyHolder
		);
		
		return keyHolder.getKey().longValue();
	}
	
	// creating PreparedStatement
	public PreparedStatementCreator psc (final String SQL, final HashMap<String, String> items) {
		return new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
				int i = 1;
				for (String value : items.values()) {
					ps.setString(i, value);
					i++;
				}
				return ps;
			}
		};
	}
	
	/**
	 * create where sql string from HashMap
	 * @param where HashMap of wheres
	 * @return where sql String
	 */
	
	public String getWhereSQLFromMap (HashMap<String, String> where) {
		if(where == null) {
			return "";
		}
		
		List whereList = new LinkedList<String>(); 
		
		for (Map.Entry<String, String> entry : where.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			whereList.add(key + " = '" + value + "'");
		}
		
		return " WHERE " + Joiner.on(",").join(whereList);
	}
	
	

}
