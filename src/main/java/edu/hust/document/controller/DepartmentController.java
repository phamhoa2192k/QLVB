package edu.hust.document.controller;

import com.google.gson.Gson;
import edu.hust.document.dto.DepartmentDTO;
import edu.hust.document.entity.DepartmentEntity;
import edu.hust.document.entity.UserEntity;
import edu.hust.document.form.DepartmentForm;
import edu.hust.document.mapper.DepartmentMapper;
import edu.hust.document.service.DepartmentService;
import edu.hust.document.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<Object> findAll() {
        List<DepartmentDTO> departmentDTOList = departmentService.findAll();
        return ResponseEntity.ok(departmentDTOList);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> findByNameLike(@RequestParam(name = "name") String name) {
        List<DepartmentDTO> departmentDTOList = departmentService.findByNameLike(name);
        if (departmentDTOList.size() == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(departmentDTOList);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> insertDepartment(@RequestBody DepartmentForm departmentForm) {
        DepartmentEntity departmentEntity = departmentService.insert(departmentForm);
        if (departmentEntity != null) {
            UserEntity user = departmentEntity.getManager();
            user.setDepartment(departmentEntity);
            userService.updateUserEntity(user);
            return ResponseEntity.ok(DepartmentMapper.departmentEntityToDTO(departmentEntity));
        }

        String s = "Không thể chọn người này làm trưởng phòng";
        return new ResponseEntity<>(new Gson().toJson(s), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateDepartment(@RequestBody DepartmentForm departmentForm) {
        DepartmentEntity departmentEntity = departmentService.update(departmentForm);
        if (departmentEntity != null) {
            UserEntity user = departmentEntity.getManager();
            user.setDepartment(departmentEntity);
            userService.updateUserEntity(user);
            return ResponseEntity.ok(DepartmentMapper.departmentEntityToDTO(departmentEntity));
        }

        String s = "Không thể update";
        return new ResponseEntity<>(new Gson().toJson(s), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable Long id) {
        DepartmentEntity departmentEntity = departmentService.delete(id);

        if (departmentEntity != null) {
            return ResponseEntity.ok(DepartmentMapper.departmentEntityToDTO(departmentEntity));
        }

        String s = "Không tìm thấy department";
        return new ResponseEntity<>(new Gson().toJson(s), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
