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
			else if(role.equals("USER")){
				return "redirect:/user/index";
			}
			else
				return "redirect:/error";
		}
		else {
			return "redirect:/403";
		}
	}
}
