package org.eurovending.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
	// Connection the database
				public Connection getConnection()  {
					Properties connectionsProps = new Properties();
					connectionsProps.put("user", "root");
					//connectionsProps.put("user", "electros_root");
					//connectionsProps.put("password", "*2Go9x}cZ_M}");
					connectionsProps.put("password", "wcv_pT273l(B"); //Pass Mysql birou
					//connectionsProps.put("password", "azmkCgSu941x#"); //MySql europartsvending wan
					//connectionsProps.put("password", "Uk6J2D27u0No#"); //Pass Mysql inoviSmartApp
					
					try {
						Class.forName("com.mysql.jdbc.Driver");
						return DriverManager.getConnection("jdbc:mysql://localhost/inovi_smart?createDatabaseIfNotExist=true",connectionsProps);
						//return DriverManager.getConnection("jdbc:mysql://localhost/electros_app_new_eurovending?createDatabaseIfNotExist=true",connectionsProps);
					} catch (SQLException |ClassNotFoundException e) {
						e.printStackTrace();

					}
					return null;
				}
						public void closeConnection(Connection conn) {
							try {
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();

							}
						}
}
