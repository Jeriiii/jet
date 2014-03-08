/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import cz.jet.models.TestObject;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Honza
 */
public class TestMapper implements RowMapper<TestObject>{
    
   @Override
   public TestObject mapRow(ResultSet rs, int rowNum) throws SQLException {
      TestObject test = new TestObject();
      test.setId(rs.getInt("id"));
      test.setName(rs.getString("name"));
      test.setAge(rs.getInt("age"));
      return test;
   }

    

    
}
