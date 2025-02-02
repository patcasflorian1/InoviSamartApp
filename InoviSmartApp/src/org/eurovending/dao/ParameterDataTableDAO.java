package org.eurovending.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
import org.eurovending.pojo.ParameterDataTable;

public class ParameterDataTableDAO {
	
	
	public void createTableParamLocation(int idLocation,String monthly,String year) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String insertLocationList = "CREATE TABLE IF NOT EXISTS param_idlocation_"+idLocation+"__month_"+monthly+"_year_"+year+
				" (id INTEGER not NULL auto_increment primary key,"
				+ "temperature DECIMAL(10,2) NULL,humidity DECIMAL(10,2) NULL,light DECIMAL(10,2) NULL,"
				+ "pollution_co DECIMAL(10,2) NULL,pollution_sound DECIMAL(10,2) NULL,"
				+ "data_entry VARCHAR(50) NOT NULL,time_entry VARCHAR(50) NOT NULL) ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 120000";	
		Statement stmt = con.createStatement();		      
	       stmt.execute(insertLocationList);
	       helper.closeConnection(con);	       
	}
	
	//verify if TableParamLocatio is Empty
	public int verifyTableParamLocation(int idLocation,String monthly,String year) throws SQLException {
		
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String selectUser = "SELECT * FROM param_idlocation_"+idLocation+"__month_"+monthly+"_year_"+year;
		int count = 0;	
	    Statement stmt = (PreparedStatement) con.prepareStatement(selectUser);
	    ResultSet rst = stmt.executeQuery(selectUser);
	        try {
	             while(rst.next()){
	                count++;
	             }
	            
	        } catch (SQLException ex) {
	                
	         }
	  
	     helper.closeConnection(con);
	     return count;
	}

	//Insert  into table LocationList
	public void insertTableParamLocation(ParameterDataTable paramLocation) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
		String paymentDate =zonedDateTime.toString();	
		 String regex = "[T.]";
		String myArray[] = paymentDate.split(regex); 
		// paymentDate = myArray[0]+" "+myArray[1];
		String insertLocation = "insert into param_idlocation_"+paramLocation.getIdLocation()+"__month_"+paramLocation.getMonthly()+"_year_"+paramLocation.getYear()+
				"(temperature,humidity,light,pollution_co,pollution_sound,data_entry,time_entry)"
				  +" value(?,?,?,?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(insertLocation);
		ps.setDouble(1, paramLocation.getTemperature());
		ps.setDouble(2, paramLocation.getHumidity());
		ps.setDouble(3, paramLocation.getLight());
		ps.setDouble(4, paramLocation.getPollutionCo());
		ps.setDouble(5, paramLocation.getPollutionSound());
		ps.setString(6, myArray[0]);
		ps.setString(7,myArray[1]);
		ps.executeUpdate();
		
		 helper.closeConnection(con);
	      }	
	

	//get allLocationList
	public ArrayList<ParameterDataTable> getAllParamLocation(int idLocation,String monthly,String year) throws SQLException{
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		ParameterDataTable chReg = new ParameterDataTable();
		ArrayList<ParameterDataTable> chRegList = new ArrayList<ParameterDataTable>();
		String strSql = "SELECT * FROM param_idlocation_"+idLocation+"__month_"+monthly+"_year_"+year;
		Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(strSql);
	      int index = 0;
	      while(rs.next()) {
	    	  int id = rs.getInt("id"); 
	    	  double temperature=rs.getDouble("temperature");
	    	  double humidity=rs.getDouble("humidity");
	    	  double light=rs.getDouble("light");
	    	  double pollutionCo=rs.getDouble("pollution_co");
	    	  double pollutionSound=rs.getDouble("pollution_sound");
	    	  String dataEntry=rs.getString("data_entry");
	    	  String timeEntry=rs.getString("time_entry");
	    	  chReg = new ParameterDataTable(id,temperature,humidity,light,pollutionCo,pollutionSound,dataEntry,timeEntry);
	    	
	    	  
	    	 chRegList.add(chReg);
	    	 
	      }
	      helper.closeConnection(con);
		return chRegList;
		
}
	//get allLocationList
		public ArrayList<ParameterDataTable> getAllParamLocationbyDateEntry(int idLocation,String monthly,String year,String dateEntry) throws SQLException{
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			ParameterDataTable chReg = new ParameterDataTable();
			ArrayList<ParameterDataTable> chRegList = new ArrayList<ParameterDataTable>();		
			String strSql = "SELECT * FROM param_idlocation_"+idLocation+"__month_"+monthly+"_year_"+year +" WHERE data_entry = '"+dateEntry+"'";
			Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery(strSql);
		      while(rs.next()) {
		    	  //System.out.println("DateEntry "+dateEntry.length()); 
		    	  int id = rs.getInt("id"); 
		    	  double temperature=rs.getDouble("temperature");
		    	  double humidity=rs.getDouble("humidity");
		    	  double light=rs.getDouble("light");
		    	  double pollutionCo=rs.getDouble("pollution_co");
		    	  double pollutionSound=rs.getDouble("pollution_sound");
		    	  String dataEntry=rs.getString("data_entry");
		    	  String timeEntry=rs.getString("time_entry");
		    	  chReg = new ParameterDataTable(id,temperature,humidity,light,pollutionCo,pollutionSound,dataEntry,timeEntry);
		    	 chRegList.add(chReg);
		    	 
		      }
		      helper.closeConnection(con);
			return chRegList;
			
	}
		
		public boolean verifyParamLocationTableIfExist(int idLocation,String monthly,String year) throws SQLException {
			boolean result = false;
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			 String companyData = "SHOW FULL TABLES FROM inovi_smart ";
			 Statement stmt = (PreparedStatement) con.prepareStatement(companyData);
			  ResultSet resultSet = stmt.executeQuery(companyData);	 
			  while (resultSet.next()) {				  
				    String tableName = resultSet.getString(1);		 
				    //System.out.println("Table : " + tableName);
				    if(tableName.equalsIgnoreCase("param_idlocation_"+idLocation+"__month_"+monthly+"_year_"+year)) {
				    	result = true;
				    	helper.closeConnection(con);
				    	return result;
				    }
				 
				  }
			  helper.closeConnection(con);
			return result;
			
		}
		
		// updateParamLocationById
		public void updateParamLocationById(ParameterDataTable paramLocation) throws SQLException {
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
			String paymentDate =zonedDateTime.toString();
			
			 String regex = "[T.]";
			String myArray[] = paymentDate.split(regex); 
			 paymentDate = myArray[0]+" "+myArray[1];
			String insertLocation = "UPDATE  param_idlocation_"+paramLocation.getIdLocation()+"__month_"+paramLocation.getMonthly()+"_year_"+paramLocation.getYear()+ "SET temperature=?,humidity=?,light=?,pollution_co=?,pollution_sound=?,data_entry=? time_entry where id = '"+paramLocation.getId()+"'";
			PreparedStatement ps = con.prepareStatement(insertLocation);
			
			ps.setDouble(1, paramLocation.getTemperature());
			ps.setDouble(2, paramLocation.getHumidity());
			ps.setDouble(3, paramLocation.getLight());
			ps.setDouble(4, paramLocation.getPollutionCo());
			ps.setDouble(5, paramLocation.getPollutionSound());
			ps.setString(6, myArray[0]);
			ps.setString(7, myArray[1]);
			ps.executeUpdate();
			
			 helper.closeConnection(con);
		      }
		
		//Delete Row Location
		public void deleteRowParamLocationById(int id,int idLocation,String monthly,String year) throws SQLException {
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			String deleteRow = "DELETE FROM param_idlocation_"+idLocation+"__month_"+monthly+"_year_"+year +"where id = "+id;
			PreparedStatement ps = con.prepareStatement(deleteRow);
			ps.setInt(1, id);
			ps.executeUpdate();
			helper.closeConnection(con);
		}
		
		//DROP ParamLocationTable
				public void dropParamLocationTable(int idLocation,String monthly,String year) throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String deleteRow = "DROP TABLE IF EXISTS param_idlocation_"+idLocation+"__month_"+monthly+"_year_"+year;
					
					PreparedStatement ps = con.prepareStatement(deleteRow);
					ps.executeUpdate();
					
					
					helper.closeConnection(con);
					
			  }	
		public void getTableParamForDrop(int idLocation) throws SQLException{
			ArrayList<String> getTableParam = new ArrayList<String>();
			String pattern = "param_idlocation_"+idLocation;
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			String showTable =  "SHOW TABLES LIKE '" + pattern + "%'";
			 Statement stmt = (PreparedStatement) con.prepareStatement(showTable);
			  ResultSet resultSet = stmt.executeQuery(showTable);	 
			  while (resultSet.next()) {
				  String tableName = resultSet.getString(1); 
				  System.out.println("Table : " + tableName);
				  getTableParam.add(tableName);
			  }
			  for(String l:getTableParam) {
				    System.out.println("DropTable : " + l);
				    stmt.execute("DROP TABLE " + l);
				 }

		}
		
}
