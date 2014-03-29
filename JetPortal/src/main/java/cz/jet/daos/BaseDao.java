/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.daos;

import cz.jet.models.database.Item;
import cz.jet.models.database.Items;
import javax.sql.DataSource;
import cz.jet.services.BaseService;
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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Petr Kukrál
 */
public class BaseDao implements BaseService {

	protected DataSource dataSource;
	protected JdbcTemplate jdbcTemplateObject;
	protected HashMap<String, String> items; 
	
	public static final String POM_ITEMS_TABLE = "pom_items";
        public static final String POM_RESULTS_TABLE = "pom_results";

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
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
		
		jdbcTemplateObject.update(
			psc(SQL, items), 
			keyHolder
		);
		
		return keyHolder.getKey().longValue();
	}
	
	// vytvoří PreparedStatement
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
	
	

}
