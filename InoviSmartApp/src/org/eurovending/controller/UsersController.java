package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eurovending.dao.CompanyDataDAO;
import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.ParameterDataTableDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.pojo.CompanyData;
import org.eurovending.pojo.Location;
import org.eurovending.pojo.ParameterDataTable;
import org.eurovending.pojo.User;
import org.eurovending.utils.HallUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsersController {
	AdminController sAdminControll = new AdminController();
	//listare  homeuser
	
	@RequestMapping(value="userhome.htm")
	public ModelAndView homeUser(HttpServletRequest request,
			HttpServletResponse response, Model model)
			throws SQLException ,ServletException, IOException, ParseException
			 {
		User myUser = new User();
		 ArrayList<Location> locationList = new ArrayList<Location>();
		 ArrayList<Location> finalLocationList = new ArrayList<Location>();
		 
		 LocationDAO locDao = new LocationDAO();
		 HttpSession session = request.getSession();
		 UserDAO udao = new UserDAO();
	      	String bucDate = sAdminControll.dateTime();
		boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
		if(getMyUserNameIsOk==true) {
		if((LoginController.getMyEmail().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
			
			myUser = udao.getEmailUser(LoginController.getMyEmail());
			locationList = locDao.getAllLocation() ;
	finalLocationList = sAdminControll.isLoginTest(locationList);
	
	 model.addAttribute("user",myUser.getFullName());
	 model.addAttribute("europeDateTime",bucDate);
	 model.addAttribute("locationList",finalLocationList);
	
		return new ModelAndView("WEB-INF/user/homeuser.jsp","model",model);
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

	int rezolutionIndex = 10;
	//listare  homeSuperAdmin
	@RequestMapping(value="view-user-location.htm")
	public ModelAndView viewUserLocation(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("id") int idLocation,Model model,BindingResult result)
			throws SQLException ,ServletException, IOException, ParseException
			 {
		int index = 0 ;
		 ArrayList<ParameterDataTable> paramDataList = new ArrayList<ParameterDataTable>();
		 ArrayList<ParameterDataTable> newParamDataList = new ArrayList<ParameterDataTable>();
		 ParameterDataTableDAO paramDao = new ParameterDataTableDAO();
		String bucDate = sAdminControll.dateTime();
		LocalDate currentdate = LocalDate.now();
		Month month = currentdate.getMonth();
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
		String paymentDate =zonedDateTime.toString();		
		 String regex = "[T.]";
		String myArray[] = paymentDate.split(regex); 
		 String monthName =month.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro") );
		 String currentYear =Integer.toString(currentdate.getYear());
		User myUser = new User();
		 UserDAO udao = new UserDAO();
		LocationDAO ldao = new LocationDAO();
		Location getLocation = new Location();
		getLocation = ldao.getLocation(idLocation);
		 HttpSession session = request.getSession();		
		boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
		if(getMyUserNameIsOk==true) {
		if((LoginController.getMyEmail().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
			getLocation = ldao.getLocation(idLocation);
			myUser = udao.getEmailUser(LoginController.getMyEmail());			
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
				 model.addAttribute("user",myUser.getFullName());
				 model.addAttribute("europeDateTime",bucDate);
				 model.addAttribute("getLocation",getLocation);
				 model.addAttribute("paramDataList",newParamDataList);
			return new ModelAndView("WEB-INF/user/view-user-location.jsp","model",model);
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

	@RequestMapping("list-paramLocationUser.htm")
	public ModelAndView viewLocation(HttpServletRequest request,HttpServletRequest response,Model model) throws SQLException {
		int index = 0 ;
		User myUser = new User();
		int idLocation = Integer.parseInt(request.getParameter("id"));
		String date = request.getParameter("date");
		int resolutionTable = Integer.parseInt(request.getParameter("resolutionTable"));
		String regex = "[-]";
		  // using split() method
        String[] arrayDate = date.split(regex);
        int monthNam = Integer.parseInt(arrayDate[1]);
		String bucDate = sAdminControll.dateTime();
		LocalDate currentdate = LocalDate.now();
		Month month = Month.of(monthNam);
		 String monthName =month.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro") );
		 String currentYear = arrayDate[0];
		
		LocationDAO ldao = new LocationDAO();
		Location getLocation = new Location();
		UserDAO udao = new UserDAO();
		getLocation = ldao.getLocation(idLocation);
		 HttpSession session = request.getSession();
		 ArrayList<ParameterDataTable> paramDataList = new ArrayList<ParameterDataTable>();
		 ArrayList<ParameterDataTable> newParamDataList = new ArrayList<ParameterDataTable>();
		 ParameterDataTableDAO paramDao = new ParameterDataTableDAO();
		boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
		if(getMyUserNameIsOk==true) {
		if((LoginController.getMyEmail().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
			getLocation = ldao.getLocation(idLocation);
			myUser = udao.getEmailUser(LoginController.getMyEmail());
			
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
			 model.addAttribute("user",myUser.getFullName());
			 model.addAttribute("europeDateTime",bucDate);
			 model.addAttribute("getLocation",getLocation);
			 model.addAttribute("paramDataList",newParamDataList);
			return new ModelAndView("WEB-INF/user/view-user-location.jsp","model",model);
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
	
	
	
	//View company data
	@RequestMapping(value="user-view-company.htm")
	public ModelAndView viewCompany(HttpServletRequest request,
			HttpServletResponse response, Model model) throws SQLException {
		User myUser = new User();
		 UserDAO udao = new UserDAO();
		 
		 HttpSession session = request.getSession();	
		boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
		if((LoginController.getMyEmail().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
			myUser = udao.getEmailUser(LoginController.getMyEmail());
			CompanyDataDAO compdao = new CompanyDataDAO();
			ArrayList <CompanyData> myCompanyData = new ArrayList<CompanyData>();
				myCompanyData = compdao.getCompanyDataList();
				String bucDate = sAdminControll.dateTime();
		 model.addAttribute("myCompanyData",myCompanyData);
		 model.addAttribute("user",myUser.getFullName());
		 model.addAttribute("europeDateTime",bucDate);
			return new ModelAndView("WEB-INF/user/viewcompany.jsp","model",model);
		}
		else {
			LoginController.isLoginAdmin = false;
			 return new ModelAndView("redirect:/LoginOut.htm");
		}
		}
}
