package edu.hust.document.service;

import edu.hust.document.dto.DepartmentDTO;
import edu.hust.document.form.DepartmentForm;

import java.util.List;

public interface IDepartmentService {
    List<DepartmentDTO> findAll();

    DepartmentDTO findById(Long id);

    List<DepartmentDTO> findByNameLike(String name);

    DepartmentDTO insert(DepartmentForm departmentForm);

    DepartmentDTO update(DepartmentForm departmentForm);

    DepartmentDTO delete(Long id);

}
