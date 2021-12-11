package edu.hust.document.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.hust.document.entity.DepartmentEntity;
import edu.hust.document.repository.DepartmentRepository;

@Controller
public class BasicAdminController {
	@Autowired
	private DepartmentRepository departmentRepository;

	@GetMapping("/admin/index")
	public String getAdminPage(){
		return "admin/index";
	}

	@GetMapping("/admin/departmentmanager")
	public String getDepartmentManagerPage(Model model, Authentication authentication){
		return "admin/qlpbAdmin";
	}

	@PostMapping("/admin/departmentmanager/add-department")
	public String handleAddDepartment(@ModelAttribute(name = "adddepartment") DepartmentEntity department, Model model){
		departmentRepository.save(department);
		return "redirect:/admin/departmentmanager";
	}
}
