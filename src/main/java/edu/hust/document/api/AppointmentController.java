package edu.hust.document.api;

import com.google.gson.Gson;
import edu.hust.document.dto.AppointmentDTO;
import edu.hust.document.entity.AppointmentEntity;
import edu.hust.document.entity.HandlingEntity;
import edu.hust.document.service.AppointmentService;
import edu.hust.document.service.HandlingService;
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

    @Autowired
    private HandlingService handlingService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<Object> findAll() {
        List<AppointmentEntity> appointmentDTOS = appointmentService.findAll();
        return ResponseEntity.ok(appointmentDTOS);
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Object> findByNameLike(@PathVariable Long id) {
        List<HandlingEntity> handlingEntities = handlingService.findByAppointmentId(id);
        return ResponseEntity.ok(handlingEntities);
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
