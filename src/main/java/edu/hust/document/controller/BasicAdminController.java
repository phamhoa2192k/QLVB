package edu.hust.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BasicAdminController {

	@GetMapping("/admin/index")
	public String getAdminPage(){
		return "admin/index";
	}

	@GetMapping("/admin/qlpbAdmin")
	public String getDepartmentManagerPage(){
		return "admin/qlpbAdmin";
	}

	@GetMapping("/admin/profile**")
	public String getProfileUserPage(){
		return "admin/profile";
	}

	@GetMapping("/admin/vbdAdmin")
	public String getVBDPage(){
		return "admin/vbdAdmin";
	}

	@GetMapping("/admin/incomingDocumentDetail**")
	public String getDocumentDetailPage(){
		return "admin/incomingDocumentDetail";
	}

	@GetMapping("/admin/vbphAdmin")
	public String getvbphAdmin(){
		return "admin/vbphAdmin";
	}

	@GetMapping("/admin/outgoingDocumentDetail**")
	public String getOutgoingDocumentDetail(){
		return "admin/outgoingDocumentDetail";
	}

}
