package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.ParameterDataTableDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.pojo.Location;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@MultipartConfig(maxFileSize = 16177215)
public class LocationController {

	 @RequestMapping(value ="editlocation.htm")
	 public ModelAndView editLocation(HttpServletRequest request,@ModelAttribute("id") Location location,Model model,BindingResult result)
	    		throws SQLException ,ServletException, IOException
	    {
		 AdminController sadm = new AdminController();
		 ArrayList<Location> newLocationList = new ArrayList<Location>();
		 LocationDAO locDao = new LocationDAO();
		 UserDAO udao = new UserDAO();
		 Location getLocation = new Location();
	      	String bucDate = sadm.dateTime();   
	      	 HttpSession session = request.getSession();
	      	 
	      	boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
			if(getMyUserNameIsOk==true) {
			if((LoginController.getMyEmail().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
				locDao.updateLocationName(location);
				getLocation = locDao.getLocation(location.getId());
				String buttonAddUser = "NotNull";
				int id = location.getId();
				 model.addAttribute("id",id);	
				return new ModelAndView("redirect:/view-location.htm","model",model);
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
	 @RequestMapping(value ="deletelocation.htm")
	 public ModelAndView deleteLocation(HttpServletRequest request,@ModelAttribute("id") Location location,Model model,BindingResult result)
	    		throws SQLException ,ServletException, IOException
	    {
		
		 ParameterDataTableDAO paramDao = new ParameterDataTableDAO();
		 ArrayList<Location> newLocationList = new ArrayList<Location>();
		 LocationDAO locDao = new LocationDAO();
		 UserDAO udao = new UserDAO();
	      	 HttpSession session = request.getSession();	      
	      	boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
			if(getMyUserNameIsOk==true) {
			if((LoginController.getMyEmail().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
				paramDao.getTableParamForDrop(location.getId());
				locDao.deleteRowLocation(location.getId());
										
				return new ModelAndView("redirect:/home-admin.htm");
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
