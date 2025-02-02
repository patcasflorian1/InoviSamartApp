package org.eurovending.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.eurovending.dao.CompanyDataDAO;
import org.eurovending.pojo.CompanyData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompanyDataController {
	
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
	
	//View company data
		@RequestMapping(value="admin-view-company.htm")
		public ModelAndView viewCompany(Model model) throws SQLException {
			 System.out.println("Company"+LoginController.getMyUserName()+": "+LoginController.isLoginAdmin);
			boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
			if((LoginController.isLoginAdmin == true)&&(getMyUserNameIsOk=true)) {
				String bucDate = dateTime();
				CompanyDataDAO compdao = new CompanyDataDAO();
				ArrayList <CompanyData> myCompanyData = new ArrayList<CompanyData>();
					myCompanyData = compdao.getCompanyDataList();
				String msg = "NotNull";
				String buttonAdd = null;
				if(compdao.verifyCompanyData() == 0) {
					buttonAdd = "Add";
				}
			 model.addAttribute("user", LoginController.getMyUserName());
			 model.addAttribute("europeDateTime",bucDate);
			 model.addAttribute("myCompanyData",myCompanyData);
			 model.addAttribute("msg",msg);
			 model.addAttribute("buttonAdd",buttonAdd);
				return new ModelAndView("WEB-INF/admin/viewcompany.jsp","model",model);
			}
			else {
				LoginController.isLoginAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}
		
		//Add company data
		@RequestMapping(value="addcompany.htm")
		public ModelAndView addCompany( @ModelAttribute("myCompany") CompanyData myComp,BindingResult result, Model model ) throws SQLException {
			
			boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
			if((LoginController.isLoginAdmin == true)&&(getMyUserNameIsOk=true)) {
				CompanyDataDAO compdao = new CompanyDataDAO();
				String buttonAdd = null;
				if(compdao.verifyCompanyData() == 0) {
				compdao.insertCompanyData(myComp);
				
				}
				ArrayList <CompanyData> myCompanyData = new ArrayList<CompanyData>();
					myCompanyData = compdao.getCompanyDataList();
					String msg = "NotNull";
				
			 model.addAttribute("myCompanyData",myCompanyData);
			 model.addAttribute("msg",msg);
			 model.addAttribute("buttonAdd",buttonAdd);
				return new ModelAndView("WEB-INF/admin/viewcompany.jsp","model",model);
			}
			else {
				LoginController.isLoginAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}
		
		//Edit company data
			@RequestMapping(value="editcompany.htm")
			public ModelAndView EditCompany( @ModelAttribute("myCompany") CompanyData myComp,BindingResult result, Model model ) throws SQLException {
				
				boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
				if((LoginController.isLoginAdmin == true)&&(getMyUserNameIsOk=true)) {
					CompanyDataDAO compdao = new CompanyDataDAO();
					String buttonAdd = null;
					
					compdao.updateCompanyData(myComp);
					
				
					ArrayList <CompanyData> myCompanyData = new ArrayList<CompanyData>();
						myCompanyData = compdao.getCompanyDataList();
						String msg = "NotNull";
					
				 model.addAttribute("myCompanyData",myCompanyData);
				 model.addAttribute("msg",msg);
				 model.addAttribute("buttonAdd",buttonAdd);
					return new ModelAndView("WEB-INF/admin/viewcompany.jsp","model",model);
				}
				else {
					LoginController.isLoginAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
				}
	
	
  }
