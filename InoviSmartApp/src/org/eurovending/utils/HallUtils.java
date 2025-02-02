package org.eurovending.utils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.eurovending.dao.LocationDAO;
import org.eurovending.pojo.InterfaceParam;
import org.eurovending.pojo.Location;


public class HallUtils {
	
	LocationDAO locdao = new LocationDAO();
	Location loc = new Location();
	InterfaceParam intrfParam = new InterfaceParam();
	ArrayList<Location> locList = new ArrayList<Location>();
	ServiceLoger serviceLoger = new ServiceLoger();
	
	//update location with param
		public void updateContorHall(Location location) throws SQLException {
			LocalDateTime now = LocalDateTime.now();
			ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
			String currentMonth = null ; 
			String currentDay = null ;
			String year = Integer.toString(zonedDateTime.getYear()) ;
			
			if(zonedDateTime.getMonthValue()<10) {
				currentMonth = 0+Integer.toString(zonedDateTime.getMonthValue());
			}else {
				currentMonth = Integer.toString(zonedDateTime.getMonthValue());
			}
			if(zonedDateTime.getDayOfMonth()<10) {
				currentDay = 0+Integer.toString(zonedDateTime.getDayOfMonth());
			}else {
				currentDay = Integer.toString(zonedDateTime.getDayOfMonth());
			}
			loc =locdao.getLocationByMacAddr(intrfParam.getMacAddress());
			
			locdao.updateLocationName(loc);
		}
		
		public int totalMachine(ArrayList<Location> locationList) {
			int totalMachine = 0;
			for(Location l:locationList) {
				if(l.getInterfaceParam().getIsLogin().equalsIgnoreCase("OnLine")) {
				totalMachine ++;
				}
			}
			
			return totalMachine;
			
		}
}
