package edu.hust.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.hust.document.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
}
