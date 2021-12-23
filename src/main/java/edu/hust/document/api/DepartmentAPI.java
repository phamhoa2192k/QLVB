package edu.hust.document.api;

import edu.hust.document.dto.DepartmentDTO;
import edu.hust.document.form.DepartmentForm;
import edu.hust.document.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/department")
public class DepartmentAPI {
    @Autowired
    private IDepartmentService departmentService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<Object> findAll() {
        List<DepartmentDTO> departmentDTOList = departmentService.findAll();
        return ResponseEntity.ok(departmentDTOList);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> findByNameLike(@RequestParam(name = "name") String name) {
        List<DepartmentDTO> departmentDTOList = departmentService.findByNameLike(name);
        return ResponseEntity.ok(departmentDTOList);
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        DepartmentDTO departmentDTO = departmentService.findById(id);
        return ResponseEntity.ok(departmentDTO);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> insertDepartment(@RequestBody DepartmentForm departmentForm) {
        DepartmentDTO departmentDTO = departmentService.insert(departmentForm);

        return ResponseEntity.ok(departmentDTO);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateDepartment(@RequestBody DepartmentForm departmentForm) {
        DepartmentDTO departmentDTO = departmentService.update(departmentForm);
        return ResponseEntity.ok(departmentDTO);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable Long id) {
        DepartmentDTO departmentDTO = departmentService.delete(id);
        return ResponseEntity.ok(departmentDTO);
    }
}
