package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.NewInterfaceDAO;
import org.eurovending.dao.ParameterDataTableDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.pojo.InterfaceParam;
import org.eurovending.pojo.Location;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InterfaceController {
	NewInterfaceDAO interfdao = new NewInterfaceDAO();
	LocationDAO locdao = new LocationDAO();
	@RequestMapping(value="interface-list.htm")
	public ModelAndView newInterfaceList(Model model) throws SQLException {
		ArrayList<InterfaceParam> interfaceParamList = new ArrayList<InterfaceParam>();
		interfaceParamList = interfdao.getAllNewInterface();
		ArrayList<Location> locationList = new ArrayList<Location>();
		locationList = locdao.getAllLocation();
		model.addAttribute("interfaceParamList", interfaceParamList);
		 model.addAttribute("locationList", locationList);
		return new ModelAndView("/WEB-INF/admin/newinterfacelist.jsp","model",model);
		
	}
	
	@RequestMapping(value="setLocation.htm")
	public ModelAndView setLocation(HttpServletRequest request,HttpServletRequest response,Model model) throws SQLException {
		
		Location location = locdao.getLocationByName(request.getParameter("locationName"));
		InterfaceParam interfParam = interfdao.getInterfaceByMacAddr(request.getParameter("macAddress"));
		interfParam.setIsLogin("OffLine");
		location.setInterfaceParam(interfParam);
		locdao.updateLocationParamById(location);
		interfdao.deleteRowInterfaceParam(interfParam.getIdInterface());
		ArrayList<InterfaceParam> interfaceParamList = new ArrayList<InterfaceParam>();
		interfaceParamList = interfdao.getAllNewInterface();
		ArrayList<Location> locationList = new ArrayList<Location>();
		locationList = locdao.getAllLocation();
		model.addAttribute("interfaceParamList", interfaceParamList);
		 model.addAttribute("locationList", locationList);
		return new ModelAndView("/WEB-INF/admin/newinterfacelist.jsp","model",model);
		
	}
	
	 @RequestMapping(value ="deleteInterface.htm")
	 public ModelAndView deleteInterface(HttpServletRequest request,@ModelAttribute("idInterface") InterfaceParam interf,Model model,BindingResult result)
	    		throws SQLException ,ServletException, IOException
	    {
		
		    NewInterfaceDAO inrfDao = new NewInterfaceDAO();
	      	 HttpSession session = request.getSession();	      
	      	boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
			if(getMyUserNameIsOk==true) {
			if((LoginController.getMyEmail().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {
				inrfDao.deleteRowInterfaceParam(interf.idInterface);
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
