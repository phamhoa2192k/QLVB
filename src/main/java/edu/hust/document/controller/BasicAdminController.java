package edu.hust.document.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.hust.document.model.Department;
import edu.hust.document.repository.DepartmentRepository;

@Controller
public class BasicAdminController {
	@Autowired
	private DepartmentRepository departmentRepository;

	@GetMapping("/admin/index")
	public String getAdminPage(Model model, Authentication authentication){
		model.addAttribute("nameofuser", ((UserDetails) authentication.getPrincipal()).getUsername());
		return "admin/index";
	}

	@GetMapping("/admin/departmentmanager")
	public String getDepartmentManagerPage(Model model, Authentication authentication){
		ArrayList<Department> allDepartment =(ArrayList<Department>) departmentRepository.findAll();
		model.addAttribute("adddepartment", new Department());
		model.addAttribute("allDepartments", allDepartment);
		model.addAttribute("nameofuser", ((UserDetails) authentication.getPrincipal()).getUsername());
		return "admin/qlpbAdmin";
	}

	@PostMapping("/admin/departmentmanager/add-department")
	public String handleAddDepartment(@ModelAttribute(name = "adddepartment") Department department, Model model){
		departmentRepository.save(department);
		return "redirect:/admin/departmentmanager";
	}
}
