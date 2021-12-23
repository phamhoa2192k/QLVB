package edu.hust.document.service;

import edu.hust.document.dto.AppointmentDTO;
import edu.hust.document.entity.AppointmentEntity;
import edu.hust.document.form.AppointmentForm;

import java.util.List;

public interface IAppointmentService {
    List<AppointmentDTO> findAll();

    List<AppointmentDTO> findLikeByName(String name);

    AppointmentDTO findById(Long id);

    AppointmentEntity findEntityById(Long id);

    AppointmentDTO insert(AppointmentForm appointmentForm);

    AppointmentDTO update(AppointmentForm appointmentForm);

    AppointmentDTO delete(Long id);
}
