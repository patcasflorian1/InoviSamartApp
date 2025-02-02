package org.eurovending.dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.eurovending.helper.DBHelper;
import org.eurovending.pojo.InterfaceParam;
import org.eurovending.pojo.Location;

public class LocationDAO {

	//Creare Tabela lista locatii/user
	
	InterfaceParam interfParam = new InterfaceParam(); ;

			public void createTableLocationList() throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String insertLocationList = "CREATE TABLE IF NOT EXISTS lisatalocatii"+
							" (id INTEGER not NULL auto_increment primary key,location_name VARCHAR(250) NOT NULL,location_adress VARCHAR(550) NULL,"
							+ "mac_address VARCHAR(50) NOT NULL,temperature DECIMAL(10,2) NULL,humidity DECIMAL(10,2) NULL,light DECIMAL(10,2) NULL,"
							+ "pollution_co DECIMAL(10,2) NULL,pollution_gases DECIMAL(10,2) NULL,signal_level INTEGER NULL,"
							+ "status VARCHAR(50) NULL,data_entry VARCHAR(50) NOT NULL) ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 2000";	
					Statement stmt = con.createStatement();		      
				       stmt.execute(insertLocationList);
				       helper.closeConnection(con);	       
				}
			
			//Insert  into table LocationList
			public void insertLocation(Location location) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
				String paymentDate =zonedDateTime.toString();
				
				 String regex = "[T.]";
				String myArray[] = paymentDate.split(regex); 
				 paymentDate = myArray[0]+" "+myArray[1];
				String insertLocation = "insert into lisatalocatii"+
						"( location_name,location_adress,mac_address,temperature,humidity,light,pollution_co,pollution_gases,signal_level,status,data_entry)"
						  +" value(?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement ps = con.prepareStatement(insertLocation);
				//ps.setInt(1, location.getId());
				ps.setString(1, location.getLocationName());
				ps.setString(2,location.getLocationAdress());
				ps.setString(3,location.getInterfaceParam().getMacAddress());
				ps.setDouble(4, location.getInterfaceParam().getTemperature());
				ps.setDouble(5, location.getInterfaceParam().getHumidity());
				ps.setDouble(6, location.getInterfaceParam().getLight());
				ps.setDouble(7, location.getInterfaceParam().getPollutionCo());
				ps.setDouble(8, location.getInterfaceParam().getPollutionGases());
				ps.setInt(9, location.getInterfaceParam().getSignalLevel());
				ps.setString(10, location.getInterfaceParam().getIsLogin());
				ps.setString(11, paymentDate);
				ps.executeUpdate();
				
				 helper.closeConnection(con);
			      }	
			

			//Insert  into table LocationList
			public void insertNewLocation(Location location) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
				String paymentDate =zonedDateTime.toString();
				
				 String regex = "[T.]";
				String myArray[] = paymentDate.split(regex); 
				 paymentDate = myArray[0]+" "+myArray[1];
				String insertLocation = "insert into lisatalocatii"+
						"( location_name,location_adress,mac_address,temperature,humidity,light,pollution_co,pollution_gases,signal_level,status,data_entry)"
						  +" value(?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement ps = con.prepareStatement(insertLocation);
				//ps.setInt(1, location.getId());
				ps.setString(1, location.getLocationName());
				ps.setString(2,location.getLocationAdress());
				ps.setString(3,"");
				ps.setDouble(4, 0);
				ps.setDouble(5, 0);
				ps.setDouble(6, 0);
				ps.setDouble(7, 0);
				ps.setDouble(8, 0);
				ps.setInt(9, 0);
				ps.setString(10, "OffLine");
				ps.setString(11, paymentDate);
				ps.executeUpdate();
				
				 helper.closeConnection(con);
			      }	

			//get allLocationList
			public ArrayList<Location> getAllLocation() throws SQLException{
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				Location chReg = new Location();
				ArrayList<Location> chRegList = new ArrayList<Location>();
				String strSql = ("SELECT * FROM lisatalocatii");
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      int index = 0;
			      while(rs.next()) {
			    	  int id = rs.getInt("id");
			    	  String locationName = rs.getString("location_name");
			    	  String locationAdress = rs.getString("location_adress");
			    	  String macAddress=rs.getString("mac_address")	;	 
			    	  double temperature=rs.getDouble("temperature");
			    	  double humidity=rs.getDouble("humidity");
			    	  double light=rs.getDouble("light");
			    	  double pollutionCo=rs.getDouble("pollution_co");
			    	  double pollutionGases=rs.getDouble("pollution_gases");
			    	  int signalLevel= rs.getInt("signal_level");
			    	  String isLogin= rs.getString("status");
			    	  String lastOnlineDate=rs.getString("data_entry");
			    	  interfParam = new InterfaceParam(macAddress,temperature,humidity,light,pollutionCo,pollutionGases,signalLevel,isLogin,lastOnlineDate);
			    	  chReg = new Location(id,locationName,locationAdress,interfParam);
			    	  
			    	 chRegList.add(chReg);
			    	 
			      }
			      helper.closeConnection(con);
				return chRegList;
				
}
			//get Location by name
			public Location getLocationByName(String locationName ) throws SQLException{
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				Location chReg = new Location();
				String strSql = ("SELECT * FROM lisatalocatii where location_name = '"+locationName+"'");
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  int id = rs.getInt("id");
			    	  String locationAdress = rs.getString("location_adress");
			    	  String macAddress=rs.getString("mac_address")	;	 
			    	  double temperature=rs.getDouble("temperature");
			    	  double humidity=rs.getDouble("humidity");
			    	  double light=rs.getDouble("light");
			    	  double pollutionCo=rs.getDouble("pollution_co");
			    	  double pollutionGases=rs.getDouble("pollution_gases");
			    	  int signalLevel= rs.getInt("signal_level");
			    	  String isLogin= rs.getString("status");
			    	  String lastOnlineDate=rs.getString("data_entry");
			    	  interfParam = new InterfaceParam(macAddress,temperature,humidity,light,pollutionCo,pollutionGases,signalLevel,isLogin,lastOnlineDate);
			    	  chReg = new Location(id,locationName,locationAdress,interfParam);
	
			      }
			      helper.closeConnection(con);
				return chReg;
			}
			
			
			//get Location by id
			public Location getLocation(int idLocation) throws SQLException{
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				Location chReg = new Location();
				String strSql = ("SELECT * FROM lisatalocatii where id = '"+idLocation+"'");
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  int id = rs.getInt("id");
			    	  String locationName = rs.getString("location_name");
			    	  String locationAdress = rs.getString("location_adress");
			    	  String macAddress=rs.getString("mac_address")	;	 
			    	  double temperature=rs.getDouble("temperature");
			    	  double humidity=rs.getDouble("humidity");
			    	  double light=rs.getDouble("light");
			    	  double pollutionCo=rs.getDouble("pollution_co");
			    	  double pollutionGases=rs.getDouble("pollution_gases");
			    	  int signalLevel= rs.getInt("signal_level");
			    	  String isLogin= rs.getString("status");
			    	  String lastOnlineDate=rs.getString("data_entry");
			    	  interfParam = new InterfaceParam(macAddress,temperature,humidity,light,pollutionCo,pollutionGases,signalLevel,isLogin,lastOnlineDate);
			    	  chReg = new Location(id,locationName,locationAdress,interfParam);
	
			      }
			      helper.closeConnection(con);
				return chReg;
			}
			
			//get Location by MacAddress
			public Location getLocationByMacAddr(String macAddres) throws SQLException{
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				Location chReg = new Location();
				String strSql = ("SELECT * FROM lisatalocatii where mac_address = '"+macAddres+"'");
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  int id = rs.getInt("id");
			    	  String locationName = rs.getString("location_name");
			    	  String locationAdress = rs.getString("location_adress");
			    	  String macAddress=rs.getString("mac_address")	;	 
			    	  double temperature=rs.getDouble("temperature");
			    	  double humidity=rs.getDouble("humidity");
			    	  double light=rs.getDouble("light");
			    	  double pollutionCo=rs.getDouble("pollution_co");
			    	  double pollutionGases=rs.getDouble("pollution_gases");
			    	  int signalLevel= rs.getInt("signal_level");
			    	  String isLogin= rs.getString("status");
			    	  String lastOnlineDate=rs.getString("data_entry");
			    	  interfParam = new InterfaceParam(macAddress,temperature,humidity,light,pollutionCo,pollutionGases,signalLevel,isLogin,lastOnlineDate);
			    	  chReg = new Location(id,locationName,locationAdress,interfParam);
			      }
			      helper.closeConnection(con);
				return chReg;
			
			}
			//get last Location
			public Location getLastLocation() throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				Location chReg = new Location();
				String strSql = ("SELECT * FROM lisatalocatii ORDER BY id DESC LIMIT 1");
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  int id = rs.getInt("id");
			    	  String locationName = rs.getString("location_name");
			    	  String locationAdress = rs.getString("location_adress");
			    	  String macAddress=rs.getString("mac_address")	;	 
			    	  double temperature=rs.getDouble("temperature");
			    	  double humidity=rs.getDouble("humidity");
			    	  double light=rs.getDouble("light");
			    	  double pollutionCo=rs.getDouble("pollution_co");
			    	  double pollutionGases=rs.getDouble("pollution_gases");
			    	  int signalLevel= rs.getInt("signal_level");
			    	  String isLogin= rs.getString("status");
			    	  String lastOnlineDate=rs.getString("data_entry");
			    	  interfParam = new InterfaceParam(macAddress,temperature,humidity,light,pollutionCo,pollutionGases,signalLevel,isLogin,lastOnlineDate);
			    	  chReg = new Location(id,locationName,locationAdress,interfParam);
			      }
			      helper.closeConnection(con);
				return chReg;
}
			
			//Update table Location by Name
			public void updateLocationName(Location location) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String updateLocationList = "UPDATE lisatalocatii"+
						" SET location_name = '"+location.getLocationName()+"',location_adress = '"+location.getLocationAdress()+"' where id = '"+location.getId()+"'";
						 
				PreparedStatement ps = con.prepareStatement(updateLocationList);
				ps.executeUpdate();	
				 helper.closeConnection(con);
			      }
		
			//Insert  into table LocationList
			public void updateLocationParamByMac(Location location) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
				String paymentDate =zonedDateTime.toString();
				
				 String regex = "[T.]";
				String myArray[] = paymentDate.split(regex); 
				 paymentDate = myArray[0]+" "+myArray[1];
				String insertLocation = "UPDATE  lisatalocatii SET temperature=?,humidity=?,light=?,pollution_co=?,pollution_gases=?,signal_level=?,status=?,data_entry=? where mac_address = '"+location.getInterfaceParam().getMacAddress()+"'";
				PreparedStatement ps = con.prepareStatement(insertLocation);
				
				ps.setDouble(1, location.getInterfaceParam().getTemperature());
				ps.setDouble(2, location.getInterfaceParam().getHumidity());
				ps.setDouble(3, location.getInterfaceParam().getLight());
				ps.setDouble(4, location.getInterfaceParam().getPollutionCo());
				ps.setDouble(5, location.getInterfaceParam().getPollutionGases());
				ps.setInt(6, location.getInterfaceParam().getSignalLevel());
				ps.setString(7, location.getInterfaceParam().getIsLogin());
				ps.setString(8, paymentDate);
				ps.executeUpdate();
				
				 helper.closeConnection(con);
			      }
		
			//Insert  into table LocationList
			public void updateLocationStatusById(Location location) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
				String paymentDate =zonedDateTime.toString();
				
				 String regex = "[T.]";
				String myArray[] = paymentDate.split(regex); 
				 paymentDate = myArray[0]+" "+myArray[1];
				String insertLocation = "UPDATE  lisatalocatii SET signal_level=?,status=? where id='"+location.getId()+"'";
				PreparedStatement ps = con.prepareStatement(insertLocation);
				ps.setInt(1, location.getInterfaceParam().getSignalLevel());
				ps.setString(2, location.getInterfaceParam().getIsLogin());
				ps.executeUpdate();
				
				 helper.closeConnection(con);
			      }
			//Insert  into table LocationList
			public void updateLocationParamById(Location location) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
				String paymentDate =zonedDateTime.toString();				
				 String regex = "[T.]";
				String myArray[] = paymentDate.split(regex); 
				 paymentDate = myArray[0]+" "+myArray[1];
				String insertLocation = "UPDATE  lisatalocatii SET location_name=?,location_adress=?,mac_address=?,temperature=?,humidity=?,light=?,pollution_co=?,pollution_gases=?,signal_level=?,status=?,data_entry=? where id='"+location.getId()+"'";
				PreparedStatement ps = con.prepareStatement(insertLocation);
				//ps.setInt(1, location.getId());
				ps.setString(1, location.getLocationName());
				ps.setString(2,location.getLocationAdress());
				ps.setString(3,location.getInterfaceParam().getMacAddress());
				ps.setDouble(4, location.getInterfaceParam().getTemperature());
				ps.setDouble(5, location.getInterfaceParam().getHumidity());
				ps.setDouble(6, location.getInterfaceParam().getLight());
				ps.setDouble(7, location.getInterfaceParam().getPollutionCo());
				ps.setDouble(8, location.getInterfaceParam().getPollutionGases());
				ps.setInt(9, location.getInterfaceParam().getSignalLevel());
				ps.setString(10, location.getInterfaceParam().getIsLogin());
				ps.setString(11, paymentDate);
				ps.executeUpdate();
				
				 helper.closeConnection(con);
			      }	
			
			//verify if table Location is empty
			public int verifyTableLocation() throws SQLException {
				
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String selectUser = "SELECT * FROM lisatalocatii ";
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
		
			
			
			
			//verify if table Location if MacAddress exist
			public int verifyTableLocationIfMacExist(Location location) throws SQLException {
				
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String selectUser = "SELECT * FROM lisatalocatii  where mac_address = '"+location.getInterfaceParam().getMacAddress()+"'";
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
			//Delete Row Location
			public void deleteRowLocation(int id) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String deleteRow = "DELETE FROM lisatalocatii WHERE id=?";
				PreparedStatement ps = con.prepareStatement(deleteRow);
				ps.setInt(1, id);
				ps.executeUpdate();
				helper.closeConnection(con);
			}
			
			//DROP TableChasRegister
			public void dropChasRegisterByUser() throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String deleteRow = "DROP TABLE IF EXISTS lisatalocatii";
				
				PreparedStatement ps = con.prepareStatement(deleteRow);
				ps.executeUpdate();
				helper.closeConnection(con);
				
		  }	
}

