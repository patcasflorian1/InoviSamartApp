package org.eurovending.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.eurovending.helper.DBHelper;
import org.eurovending.pojo.InterfaceParam;
import org.eurovending.pojo.Location;

public class NewInterfaceDAO {
	
	//Creare Tabela NewInterface
	
	public void createTableNewInterface() throws SQLException {
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			String insertLocationList = "CREATE TABLE IF NOT EXISTS new_interface"+
					" (id INTEGER not NULL auto_increment primary key,mac_address VARCHAR(50) NOT NULL,data_entry VARCHAR(50) NOT NULL"
					+ ") ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 2000";	
			Statement stmt = con.createStatement();		      
		       stmt.execute(insertLocationList);
		       helper.closeConnection(con);	       
		}

	//Insert  into table LocationList
	public void insertNewInterface(InterfaceParam interfaceParam) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
		String paymentDate =zonedDateTime.toString();
		
		 String regex = "[T.]";
		String myArray[] = paymentDate.split(regex); 
		 paymentDate = myArray[0]+" "+myArray[1];
		String insertLocation = "insert into new_interface"+
				"(mac_address,data_entry)"
				  +" value(?,?)";
		
		PreparedStatement ps = con.prepareStatement(insertLocation);
		//ps.setInt(1, location.getId());
		ps.setString(1, interfaceParam.getMacAddress());
		ps.setString(2, paymentDate);
		ps.executeUpdate();
		
		 helper.closeConnection(con);
	      }	
	
	//get allLocationList
	public ArrayList<InterfaceParam> getAllNewInterface() throws SQLException{
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		InterfaceParam interfaceParam = new InterfaceParam();
		 ArrayList<InterfaceParam> interfaceParamList = new ArrayList<InterfaceParam>();
		String strSql = ("SELECT * FROM new_interface");
		Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(strSql);
	      while(rs.next()) {
	    	  int id = rs.getInt("id");
	    	  String macAddress = rs.getString("mac_address");
	    	  String dataEntry = rs.getString("data_entry");
	    	  interfaceParam = new InterfaceParam(id,macAddress,dataEntry);
	    
	    	  interfaceParamList.add(interfaceParam);
	      }
	      helper.closeConnection(con);
		return interfaceParamList;
	}
	
	//get InterfaceParam by id
	public InterfaceParam getNewInterface(int idInterfaceParam) throws SQLException{
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		InterfaceParam interfaceParam = new InterfaceParam();
		String strSql = ("SELECT * FROM new_interface where id= '"+idInterfaceParam+"'");
		Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(strSql);
	      while(rs.next()) {
	    	  int id = rs.getInt("id");
	    	  String macAddress = rs.getString("mac_address");
	    	  String dataEntry = rs.getString("data_entry");
	    	  interfaceParam = new InterfaceParam(id,macAddress,dataEntry);
	      }
	      helper.closeConnection(con);
		return interfaceParam;
	}

	//get InterfaceParam by MacAddress
	public InterfaceParam getInterfaceByMacAddr(String macAddres) throws SQLException{
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		InterfaceParam interfaceParam = new InterfaceParam();
		String strSql = ("SELECT * FROM new_interface where mac_address = '"+macAddres+"'");
		Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(strSql);
	      while(rs.next()) {
	    	  int id = rs.getInt("id");
	    	  String macAddress = rs.getString("mac_address");
	    	  String dataEntry = rs.getString("data_entry");
	    	  interfaceParam = new InterfaceParam(id,macAddress,dataEntry);
	      }
	      helper.closeConnection(con);
		return interfaceParam;
	}
	
	//Delete Row InterfaceParam
	public void deleteRowInterfaceParam(int id) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String deleteRow = "DELETE FROM new_interface WHERE id=?";
		PreparedStatement ps = con.prepareStatement(deleteRow);
		ps.setInt(1, id);
		ps.executeUpdate();
		helper.closeConnection(con);
	}
	
	//DROP TableInterfaceParam
	public void dropTableInterfaceParam() throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String deleteRow = "DROP TABLE IF EXISTS new_interface";
		
		PreparedStatement ps = con.prepareStatement(deleteRow);
		ps.executeUpdate();
		helper.closeConnection(con);
		
  }
}
