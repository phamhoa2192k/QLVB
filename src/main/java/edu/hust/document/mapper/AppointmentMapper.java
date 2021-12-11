package edu.hust.document.mapper;

import edu.hust.document.dto.AppointmentDTO;
import edu.hust.document.entity.AppointmentEntity;

public class AppointmentMapper {
    public static AppointmentDTO appointmentEntityToDTO(AppointmentEntity appointmentEntity) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setBaseDocumentEntity(appointmentEntity.getBaseDocumentEntity());
        appointmentDTO.setSecurityLevel(appointmentEntity.getSecurityLevel());
        appointmentDTO.setUrgencyLevel(appointmentEntity.getUrgencyLevel());

        return appointmentDTO;
    }
}
