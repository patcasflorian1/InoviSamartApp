package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.NewInterfaceDAO;
import org.eurovending.dao.ParameterDataTableDAO;
import org.eurovending.pojo.InterfaceParam;
import org.eurovending.pojo.Location;
import org.eurovending.pojo.ParameterDataTable;
import org.eurovending.utils.HallUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HallController {
	LocationDAO locdao = new LocationDAO();
	NewInterfaceDAO newIntrfDao = new NewInterfaceDAO();
	Location loc = new Location();
	Location newLoc = new Location();
	InterfaceParam intrfParam = new InterfaceParam();
	 @RequestMapping(value ="getTcpData.htm", method = RequestMethod.GET)
	    public void getTcpData
(HttpServletRequest request,HttpServletRequest response,@RequestParam String macAddress,@RequestParam String temperature,@RequestParam String humidity,
	    @RequestParam String light,@RequestParam String pollutionCo,@RequestParam String pollutionGases,@RequestParam String signalLevel)
	    
	    		throws SQLException ,ServletException, IOException
	    {
		 
		 InterfaceParam intrfParamOld = new InterfaceParam();
		 System.out.println("Client Connected1");
		 loc = locdao.getLocationByMacAddr(macAddress);
		
		 intrfParam.setMacAddress(macAddress);
		 intrfParam.setTemperature(Double.parseDouble(temperature));
		 intrfParam.setHumidity(Double.parseDouble(humidity));
		 intrfParam.setLight(Double.parseDouble(light));
		 intrfParam.setPollutionCo(Double.parseDouble(pollutionCo));
		 intrfParam.setPollutionGases(Double.parseDouble(pollutionGases));
		 int levelSignal = Integer.parseInt(signalLevel);
	    	levelSignal = Math.abs(levelSignal);
	    	intrfParam.setSignalLevel(levelSignal);
	    	intrfParam.setIsLogin("OnLine");
	    	
	    	if(loc.getLocationName()== null) {	
	    		System.out.println("Client Connected2"+intrfParamOld.getMacAddress());
	    		intrfParamOld = newIntrfDao.getInterfaceByMacAddr(macAddress);	    		
	    		if(intrfParamOld.getMacAddress() == null) {	    			
		    		newIntrfDao.insertNewInterface(intrfParam);		    		
		    		}
	    	}
	    	else {
	    		
	    		loc.setInterfaceParam(intrfParam);
    			locdao.updateLocationParamByMac(loc);
    			insertParamLocation(loc.getId(),intrfParam);
    		}
	    	
	    	System.out.println("Client Connected"+" MacAdrres "+macAddress+" Temp "+temperature+" Hum "+humidity+
	    			" LevelSign "+levelSignal);    	  
	   
	    }
	
	 public void insertParamLocation(int idLocation,InterfaceParam interfaceParam) {
		 ParameterDataTableDAO paramDataDAO = new ParameterDataTableDAO();
		// LocalDateTime now = LocalDateTime.now();
		 LocalDate currentdate = LocalDate.now();
		 Month month = currentdate.getMonth();
		 String monthName =month.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro") );
		 String currentYear =Integer.toString(currentdate.getYear());
		 ParameterDataTable paramData = new ParameterDataTable(idLocation,monthName,currentYear,interfaceParam.getTemperature(),interfaceParam.getHumidity(),
				 interfaceParam.getLight(),interfaceParam.getPollutionCo(),interfaceParam.getPollutionGases());
		 try {
			paramDataDAO.createTableParamLocation(idLocation, monthName, currentYear);
			paramDataDAO.insertTableParamLocation(paramData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		   
	 }
	
	
}
