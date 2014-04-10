/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.mappers;

import cz.jet.models.PomItemEntite;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Petr Kukrál
 */
public class PomItemsMapper implements RowMapper<PomItemEntite>{
    
   @Override
   public PomItemEntite mapRow(ResultSet rs, int rowNum) throws SQLException {
      PomItemEntite pitem = new PomItemEntite();
      pitem.setId(rs.getInt("id"));
      pitem.setEmail(rs.getString("email"));
      pitem.setResult(rs.getString("result"));
      return pitem;
   }
    
}
