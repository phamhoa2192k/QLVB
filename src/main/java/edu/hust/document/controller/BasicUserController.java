package edu.hust.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class BasicUserController {
	@GetMapping("/user/vbdUser")
	public String getVBDPage(){
		return "user/vbdUser";
	}

	@GetMapping("/user/vbphUser")
	public String getvbphPage(){
		return "user/vbphUser";
	} 

	@GetMapping("/user/documentDetail**")
	public String getdocumentDetail(){
		return "user/documentDetail";
	}

}
