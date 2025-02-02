package org.eurovending.utils;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eurovending.controller.LoginController;
//import org.eurovending.dao.CompanyDataDAO;
import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.NewInterfaceDAO;
import org.eurovending.dao.UserDAO;

import org.eurovending.pojo.User;


public class ServiceLoger {
	  
	public UserDAO uDao = new UserDAO();
	public LocationDAO ldao = new LocationDAO();
	NewInterfaceDAO nIntrfDao = new NewInterfaceDAO();
	//CompanyDataDAO compDao = new CompanyDataDAO();
	public ArrayList<User> userList = new ArrayList<User>();
	public  PasswordUtils uPassword = new PasswordUtils();
	public String salt = null ;
	public String adminPassword = "eurovending";
	public String generatePassword = null;
	public void generateTable() {
		 salt = uPassword.getSalt(30);
		 generatePassword = uPassword.generateSecurePassword( adminPassword,salt);
		 User superAdmin = new User("Patcas Florin","+40743556569",
	             "patcasf12@gmail.com",salt,generatePassword,"ADMIN","ACTIVE","Florin","22.01.2025");
		 try {
				//uDao.createTableUser();
				//compDao.createTableCompanyData();
				ldao.createTableLocationList();
				nIntrfDao.createTableNewInterface();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 int count=0;
			try {
				 
				 count = uDao.verifyTableUser();
				 
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(count==0) {
				   try {
					uDao.insertUser(superAdmin);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			 }	
		}
	
	//test user&password is not empty
	public boolean testLogin(String user,String pwd) throws SQLException {
		boolean isOkUser  = false;
		boolean myEmailIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(user) ;
		boolean myPasswordIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(pwd);
		userList = uDao.getUserList();
		if((myEmailIsOk==true)&&(myPasswordIsOk==true)) {
			isOkUser  = true;
			
			return isOkUser ;
		}	
		else {
			return isOkUser;
			}
		}
	
	
	}

