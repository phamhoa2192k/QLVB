package edu.hust.document.service.impl;

import edu.hust.document.dto.AppointmentDTO;
import edu.hust.document.entity.AppointmentEntity;
import edu.hust.document.form.AppointmentForm;
import edu.hust.document.repository.AppointmentRepository;
import edu.hust.document.service.IAppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService implements IAppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AppointmentDTO> findAll() {
        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAll();
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        for (AppointmentEntity appointmentEntity: appointmentEntityList) {
            appointmentDTOList.add(modelMapper.map(appointmentEntity, AppointmentDTO.class));
        }
        return  appointmentDTOList;
    }

    @Override
    public AppointmentDTO findById(Long id) {
        AppointmentEntity appointmentEntity = appointmentRepository.findAppointmentEntityById(id);
        if (appointmentEntity == null){
            return null;
        }
        return  modelMapper.map(appointmentEntity, AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO insert(AppointmentForm appointmentForm) {
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        setAppointmentFormForEntity(appointmentForm, appointmentEntity);

        AppointmentEntity appointmentEntity1;
        try {
            appointmentEntity1 = appointmentRepository.save(appointmentEntity);
        }catch (Exception e) {
            return null;
        }


        return modelMapper.map(appointmentEntity1, AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO delete(Long id){
        AppointmentEntity appointmentEntity = appointmentRepository.findAppointmentEntityById(id);
        if (appointmentEntity == null){
            return null;
        }

        return modelMapper.map(appointmentEntity, AppointmentDTO.class);
    }

    void setAppointmentFormForEntity(AppointmentForm appointmentForm, AppointmentEntity appointmentEntity){
        appointmentEntity.setSecurityLevel(appointmentForm.getSecurityLevel());
        appointmentEntity.setUrgencyLevel(appointmentForm.getUrgencyLevel());
    }
}
