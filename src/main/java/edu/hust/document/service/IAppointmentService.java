package edu.hust.document.service;

import edu.hust.document.dto.AppointmentDTO;
import edu.hust.document.form.AppointmentForm;

import java.util.List;

public interface IAppointmentService {
    List<AppointmentDTO> findAll();

    AppointmentDTO findById(Long id);

    AppointmentDTO insert(AppointmentForm appointmentForm);

    AppointmentDTO delete(Long id);
}
