package org.eurovending.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.eurovending.dao.UserDAO;
import org.eurovending.pojo.User;
import org.eurovending.utils.PasswordUtils;
import org.eurovending.utils.ServiceLoger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminUserController {


	
	//listare  homeSuperAdmin
		@RequestMapping(value="admin-user-list.htm")
		public ModelAndView homeAdminUser(HttpServletRequest request,HttpServletRequest response,Model model) 
				throws SQLException ,ServletException, IOException
				 {
			 ArrayList<User> usersList = new ArrayList<User>();
			 ArrayList<User> newUserList = new ArrayList<User>();
			 UserDAO udao = new UserDAO();
			 usersList = udao.getUserList();
			 HttpSession session = request.getSession();
			 boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
			if((LoginController.getMyEmail().equals(session.getAttribute("email")))&&(getMyUserNameIsOk==true)) {		
		 User adminUser = udao.getEmailUser(LoginController.getMyEmail());
				for(User u : usersList) {
					u = udao.getIdUser(u.getId());
					if(u.getId()>1) {
						newUserList.add(u);
					}
				}
			
		 model.addAttribute("userList",newUserList);
		 model.addAttribute("user",LoginController.getMyUserName());
			return new ModelAndView("WEB-INF/admin/user/userlist.jsp","model",model);
		}
		else {
			LoginController.isLoginAdmin = false;
			 return new ModelAndView("redirect:/LoginOut.htm");
		}
    }
		
		@RequestMapping(value="adduseradmin.htm")
		public ModelAndView addUser(@ModelAttribute("idUser") User user, 
				Model model,BindingResult result) throws SQLException {
			
			PasswordUtils utilPassword = new PasswordUtils();
			ServiceLoger svl = new ServiceLoger();
			 ArrayList<User> userList = new ArrayList<User>();
			UserDAO udao = new UserDAO();
			 String salt = null ;
	         salt = utilPassword.getSalt(30);
	         String newPassword = utilPassword.generateSecurePassword(user.getPassword(), salt);
	         user.setOperator(LoginController.getMyUserName());
	         user.setPassword(newPassword);
	         user.setSalt(salt);
			 boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
			 if((LoginController.isLoginAdmin == true)&&(getMyUserNameIsOk==true)) {
					userList = udao.getUserList();
					for(User u: userList) {
						if((u.getFullName().equalsIgnoreCase(user.getFullName()))||(u.getEmail().equals(user.getEmail()))){
						String msgError =" Eroare UserName utilizat sau email utilizat!! Schimbati!!! ";	
						model.addAttribute("msgError",msgError);
						return new ModelAndView("WEB-INF/admin/user/usermodal.jsp","model",model);
						}
					}			
					udao.insertUser(user);
					user = udao.getLastUserInsert();						
					model.addAttribute("myUser",user);
		        		return new ModelAndView("redirect:/admin-user-list.htm");
				}
				else {
					 LoginController.isLoginAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
		}
		
		//Update User
		@RequestMapping(value="updateUser.htm")
		public ModelAndView editProduct(@ModelAttribute("idUser") User user , 
				Model model,BindingResult result) throws SQLException, ServletException, IOException {
			PasswordUtils utilPassword = new PasswordUtils();
			User newUser = new User();
				UserDAO udao = new UserDAO();
				newUser = udao.getIdUser(user.getId());
				boolean getMyUserNameIsOk =LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
				boolean getMyPassword = LoginController.isNotNullNotEmptyNotWhiteSpace(user.getPassword());
				if((LoginController.isLoginAdmin == true)&&(getMyUserNameIsOk == true)) {
					if(getMyPassword == true) {
						 String salt = null ;
				          salt = utilPassword.getSalt(30);
				          String newPassword = utilPassword.generateSecurePassword(user.getPassword(), salt);
				          user.setPassword(newPassword);
				          user.setSalt(salt);
					}
					else {
						user.setSalt(newUser.getSalt());
						user.setPassword(newUser.getPassword());
					}
					udao.updateUser(user);
					return new ModelAndView("redirect:/admin-user-list.htm");
				    }
				else {
					 LoginController.isLoginAdmin = false;
					 return new ModelAndView("redirect:/LoginOut.htm");
				}
		     }
		
		//delete User
		@RequestMapping(value="delete-user.htm")
		public ModelAndView deleteUser(@ModelAttribute("id") String idUser ,Model model,BindingResult result) 
				throws SQLException, ServletException, IOException {
			
			int id= Integer.parseInt(idUser);
			boolean getMyUserNameIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(LoginController.getMyEmail());
			if((LoginController.isLoginAdmin == true)&&(getMyUserNameIsOk==true)) {
				
				UserDAO udao = new UserDAO();						
				udao.deleteUser(id);
				return new ModelAndView("redirect:/admin-user-list.htm");
			}
			else {
				 LoginController.isLoginAdmin = false;
				 return new ModelAndView("redirect:/LoginOut.htm");
			}
			}
}
