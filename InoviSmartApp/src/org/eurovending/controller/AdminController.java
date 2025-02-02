package org.eurovending.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.eurovending.dao.CompanyDataDAO;
import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.ParameterDataTableDAO;
import org.eurovending.dao.UserDAO;
//import org.eurovending.pojo.CompanyData;
import org.eurovending.pojo.Location;
import org.eurovending.pojo.LocationStatus;
import org.eurovending.pojo.ParameterDataTable;
import org.eurovending.pojo.User;
import org.eurovending.utils.HallUtils;
import org.eurovending.utils.PasswordUtils;
import org.eurovending.utils.ServiceLoger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class AdminController {
	LocationDAO locdao = new LocationDAO();
	UserDAO udao = new UserDAO();
	
	public ArrayList<Location> isLoginTest(ArrayList<Location> locationList) throws SQLException{
		ArrayList<Location> newLocationList = new ArrayList<Location>();
		// ZoneDateTime ett = ZoneDateTime.now(ZoneId.of("Europe/Bucharest"));
		 LocalDateTime now = LocalDateTime.now();
		 // convert LocalDateTime to ZonedDateTime, with default system zone id
	      ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
	      String paymentDate =zonedDateTime.toString();			
			 String regex = "[T.]";
			String myArray[] = paymentDate.split(regex); 
			 paymentDate = myArray[0]+" "+myArray[1];
			 String hour[] = myArray[1].split(":");
			 int minute = Integer.parseInt(hour[1]);
			 LocationStatus online = LocationStatus.OnLine;
			 LocationStatus offline = LocationStatus.OffLine;
			
			 for(Location l:locationList) {
					String str = l.getInterfaceParam().getLastOnlineDate();
			        String[] arrOfStr = str.split(" ");
			        l.getInterfaceParam().setLastOnlineDate(arrOfStr[0]);
			        l.getInterfaceParam().setLastOnlineTime(arrOfStr[1]);
			        String locationHour[] = arrOfStr[1].split(":");
			        String hourLoc = locationHour[0];
			        int minuteLoc = Integer.parseInt(locationHour[1]);
			        if((arrOfStr[0].equalsIgnoreCase(myArray[0]))&&(hourLoc.equalsIgnoreCase(hour[0]))&&((minute-minuteLoc)<5)) {
			        	l.getInterfaceParam().setIsLogin(online.toString());
			        }else {
			        	l.getInterfaceParam().setIsLogin(offline.toString());
			        	l.getInterfaceParam().setSignalLevel(0);
			        }
			        locdao.updateLocationStatusById(l);
					newLocationList.add(l);
			 }
	      
		return newLocationList;
	}
	
	public String dateTime() {
		
		 DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
			// ZoneDateTime ett = ZoneDateTime.now(ZoneId.of("Europe/Bucharest"));
			 LocalDateTime now = LocalDateTime.now();
			 // convert LocalDateTime to ZonedDateTime, with default system zone id
		      ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
		      
				String paymentDate =zonedDateTime.toString();
		      // convert LocalDateTime to ZonedDateTime, with specified zoneId
		      ZonedDateTime europeDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Bucharest"));
		      
		      	String bucDate = (format.format(europeDateTime)).toString();
		return bucDate;
	}

	@RequestMapping(value="home-admin.htm")
	public ModelAndView homeAdmin(  Model model)
			throws SQLException ,ServletException, IOException, ParseException
			 {	
		ArrayList<Location> locationList = new ArrayList<Location>();
		ArrayList<Location> newLocationList = new ArrayList<Location>();
		locationList = locdao.getAllLocation();
		newLocationList = isLoginTest(locationList);
		HallUtils hutls = new HallUtils();
		String bucDate = dateTime();
		User myUser =  udao.getEmailUser(LoginController.getMyEmail());
		LoginController.setMyUserName(myUser.getFullName());
		int totalMachine = hutls.totalMachine(newLocationList);
		model.addAttribute("europeDateTime",bucDate);
		 model.addAttribute("user", LoginController.getMyUserName());
		 model.addAttribute("locationList", newLocationList);
		 model.addAttribute("totalMachine",totalMachine);
		return new ModelAndView("/WEB-INF/admin/homeadmin.jsp","model",model);
	}
	
	@RequestMapping(value="addLocation.htm")
	public ModelAndView addLocation(@ModelAttribute("locname") Location newLoc) throws SQLException {
		locdao.insertNewLocation(newLoc);	
		return new ModelAndView("redirect:/home-admin.htm");
	}
	
	int rezolutionIndex = 20;
	@RequestMapping("view-location.htm")
	public ModelAndView editLocation(HttpServletRequest request,HttpServletRequest response,Model model) throws SQLException {
		int index = 0 ;
		String bucDate = dateTime();
		LocalDate currentdate = LocalDate.now();
		Month month = currentdate.getMonth();
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
		String paymentDate =zonedDateTime.toString();		
		 String regex = "[T.]";
		String myArray[] = paymentDate.split(regex); 
		 String monthName =month.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro") );
		 String currentYear =Integer.toString(currentdate.getYear());
		int idLocation = Integer.parseInt(request.getParameter("id"));
		LocationDAO ldao = new LocationDAO();
		Location getLocation = new Location();
		getLocation = ldao.getLocation(idLocation);
		 HttpSession session = request.getSession();
		 ArrayList<ParameterDataTable> paramDataList = new ArrayList<ParameterDataTable>();
		 ArrayList<ParameterDataTable> newParamDataList = new ArrayList<ParameterDataTable>();
		 ParameterDataTableDAO paramDao = new ParameterDataTableDAO();
		
		boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
		if(getMyUserNameIsOk==true) {
		if((LoginController.getMyEmail().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
			getLocation = ldao.getLocation(idLocation);
			 boolean ifExistTable = paramDao.verifyParamLocationTableIfExist(idLocation, monthName, currentYear);
			if(ifExistTable == true) {
			paramDataList = paramDao.getAllParamLocationbyDateEntry(idLocation, monthName, currentYear, myArray[0]);	
			for(ParameterDataTable parm :paramDataList) {
				index++;
				if(index >= rezolutionIndex) {
					parm.setLight(parm.getLight()/100);
					newParamDataList.add(parm);
					index = 0;
				}
				
			}
			}
			String buttonAddUser = "NotNull";
			     model.addAttribute("buttonAddUser",buttonAddUser);	
				 model.addAttribute("nameUser",LoginController.getMyUserName());
				 model.addAttribute("paramDataList",newParamDataList);	
				 model.addAttribute("europeDateTime",bucDate);
				 model.addAttribute("getLocation",getLocation);
			return new ModelAndView("WEB-INF/admin/view-location.jsp","model",model);
		}
		else {
			LoginController.isLoginAdmin = false;
			 return new ModelAndView("redirect:/LoginOut.htm");
		}
			}
			else {
				LoginController.isLoginAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
}
	
	@RequestMapping("list-paramLocation.htm")
	public ModelAndView viewLocation(HttpServletRequest request,HttpServletRequest response,Model model) throws SQLException {
		int index = 0 ;
		
		int idLocation = Integer.parseInt(request.getParameter("id"));
		String date = request.getParameter("date"); 
		
		int resolutionTable = Integer.parseInt(request.getParameter("resolutionTable"));
		String regex = "[-]";
		  // using split() method
        String[] arrayDate = date.split(regex);
        int monthNam = Integer.parseInt(arrayDate[1]);
		String bucDate = dateTime();
		LocalDate currentdate = LocalDate.now();
		Month month = Month.of(monthNam);
		 String monthName =month.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro") );
		// String currentYear =Integer.toString(currentdate.getYear());
		 String currentYear = arrayDate[0];
		LocationDAO ldao = new LocationDAO();
		Location getLocation = new Location();
		getLocation = ldao.getLocation(idLocation);
		 HttpSession session = request.getSession();
		 ArrayList<ParameterDataTable> paramDataList = new ArrayList<ParameterDataTable>();
		 ArrayList<ParameterDataTable> newParamDataList = new ArrayList<ParameterDataTable>();
		 ParameterDataTableDAO paramDao = new ParameterDataTableDAO();
		boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
		if(getMyUserNameIsOk==true) {
		if((LoginController.getMyEmail().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
			getLocation = ldao.getLocation(idLocation);
			 boolean ifExistTable = paramDao.verifyParamLocationTableIfExist(idLocation, monthName, currentYear);			 			
			if(ifExistTable == true) {
			paramDataList = paramDao.getAllParamLocationbyDateEntry(idLocation, monthName, currentYear, date);
			for(ParameterDataTable parm :paramDataList) {
				index++;
				if(index >= resolutionTable) {
					parm.setLight(parm.getLight()/100);
					newParamDataList.add(parm);
					index = 0;
				}				
			}
		}
			String buttonAddUser = "NotNull";
			     model.addAttribute("buttonAddUser",buttonAddUser);	
				 model.addAttribute("nameUser",LoginController.getMyUserName());
				 model.addAttribute("paramDataList",newParamDataList);	
				 model.addAttribute("europeDateTime",bucDate);
				 model.addAttribute("getLocation",getLocation);
			return new ModelAndView("WEB-INF/admin/view-location.jsp","model",model);
		}
		else {
			LoginController.isLoginAdmin = false;
			 return new ModelAndView("redirect:/LoginOut.htm");
		}
			}
			else {
				LoginController.isLoginAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
}


	
	
}
