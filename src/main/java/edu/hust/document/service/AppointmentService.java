package edu.hust.document.service;

import edu.hust.document.dto.AppointmentDTO;
import edu.hust.document.entity.AppointmentEntity;
import edu.hust.document.entity.DepartmentEntity;
import edu.hust.document.entity.UserEntity;
import edu.hust.document.mapper.AppointmentMapper;
import edu.hust.document.mapper.DepartmentMapper;
import edu.hust.document.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<AppointmentEntity> findAll() {
        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAll();
//        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
//        for (AppointmentEntity appointmentEntity: appointmentEntityList) {
//            appointmentDTOList.add(AppointmentMapper.appointmentEntityToDTO(appointmentEntity));
//        }
        return  appointmentEntityList;
    }

    public AppointmentEntity delete(Long id){
        AppointmentEntity appointmentEntity = appointmentRepository.findAppointmentEntityById(id);

        if (appointmentEntity == null){
            return null;
        }

        return appointmentEntity;
    }
}
