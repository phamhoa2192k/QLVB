package edu.hust.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class BasicUserController {

	@GetMapping("/user/index")
	public String getIndex(){
		return "user/index";
	}

	@GetMapping("/user/vbdUser")
	public String getVBDPage(){
		return "user/vbdUser";
	}

	@GetMapping("/user/vbphUser")
	public String getvbphPage(){
		return "user/vbphUser";
	} 

	@GetMapping("/user/outgoingDocumentDetail**")
	public String getdocumentDetail(){
		return "user/outgoingDocumentDetail";
	}

	@GetMapping("/user/incomingDocumentDetail**")
	public String getIncomingDocumentDetail(){
		return "user/incomingDocumentDetail";
	}

}
