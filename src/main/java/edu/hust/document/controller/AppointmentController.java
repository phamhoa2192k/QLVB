package edu.hust.document.controller;

import com.google.gson.Gson;
import edu.hust.document.entity.AppointmentEntity;
import edu.hust.document.entity.DepartmentEntity;
import edu.hust.document.mapper.DepartmentMapper;
import edu.hust.document.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<Object> findAll() {
        List<AppointmentEntity> appointmentEntityList = appointmentService.findAll();
        return ResponseEntity.ok(appointmentEntityList);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteAppointment(@PathVariable Long id) {
        AppointmentEntity appointmentEntity = appointmentService.delete(id);

        if (appointmentEntity != null) {
            return ResponseEntity.ok(appointmentEntity);
        }

        String s = "Không tìm thấy appointment";
        return new ResponseEntity<>(new Gson().toJson(s), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
