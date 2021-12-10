package edu.hust.document.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	@GetMapping("/login**")
	public String getLoginPage() {
		return "login";
	}

	@GetMapping("/role")
	public String getAskRolePage() {
		return "askrole";
	}

	@PostMapping("/role")
	public String successLogin(@ModelAttribute(name = "role") String role, Authentication authentication){
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority(role))){
			if(role.equals("ADMIN")){
				return "redirect:/admin/index";
			}
			else if(role.equals("MANAGER")){
				return "redirect:/manager/index";
			}
			else if(role.equals("STAFF")){
				return "redirect:/staff/index";
			}
			else 
				return "redirect:/login";
		}
		else {
			return "redirect:/error";
		}
	}
}
