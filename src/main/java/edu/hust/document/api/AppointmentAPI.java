package edu.hust.document.api;

import edu.hust.document.dto.AppointmentDTO;
import edu.hust.document.form.AppointmentForm;
import edu.hust.document.service.IAppointmentService;
import edu.hust.document.service.impl.HandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/appointment")
public class AppointmentAPI {
    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private HandlingService handlingService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<Object> findAll() {
        List<AppointmentDTO> appointmentDTOS = appointmentService.findAll();
        return ResponseEntity.ok(appointmentDTOS);
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Object> findByNameLike(@PathVariable Long id) {
        AppointmentDTO appointmentDTO = appointmentService.findById(id);
        if (appointmentDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(appointmentDTO);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> insertAppointment(@RequestBody AppointmentForm appointmentForm) {
        AppointmentDTO appointmentDTO = appointmentService.insert(appointmentForm);
        if (appointmentDTO == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(appointmentDTO);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteAppointment(@PathVariable Long id) {
        AppointmentDTO appointmentDTO = appointmentService.delete(id);

        if (appointmentDTO == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(appointmentDTO);
    }
}
