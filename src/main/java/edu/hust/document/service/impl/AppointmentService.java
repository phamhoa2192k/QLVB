package edu.hust.document.service.impl;

import edu.hust.document.dto.AppointmentDTO;
import edu.hust.document.dto.BaseDocumentDTO;
import edu.hust.document.dto.HandlingDTO;
import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.AppointmentEntity;
import edu.hust.document.entity.BaseDocumentEntity;
import edu.hust.document.entity.HandlingEntity;
import edu.hust.document.form.AppointmentForm;
import edu.hust.document.repository.AppointmentRepository;
import edu.hust.document.service.IAppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            AppointmentDTO appointmentDTO = modelMapper.map(appointmentEntity, AppointmentDTO.class);
            BaseDocumentDTO baseDocumentDTO = modelMapper.map(appointmentEntity.getBaseDocumentEntity(),
                    BaseDocumentDTO.class);
            baseDocumentDTO.setType(appointmentEntity.getBaseDocumentEntity().getCategory().getType());
            appointmentDTO.setBaseDocumentDTO(baseDocumentDTO);
            appointmentDTOList.add(appointmentDTO);
        }
        return  appointmentDTOList;
    }

    @Override
    public AppointmentDTO findById(Long id) {
        AppointmentEntity appointmentEntity = appointmentRepository.findAppointmentEntityById(id);
        if (appointmentEntity == null){
            return null;
        }

        BaseDocumentEntity baseDocumentEntity = appointmentEntity.getBaseDocumentEntity();
        AppointmentDTO appointmentDTO = modelMapper.map(appointmentEntity, AppointmentDTO.class);
        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity, BaseDocumentDTO.class);

        Set<HandlingEntity> handlingEntitySet = baseDocumentEntity.getHandlings();
        List<HandlingDTO> handlingDTOList = new ArrayList<>();
        for (HandlingEntity handlingEntity:
                handlingEntitySet) {
            HandlingDTO handlingDTO = modelMapper.map(handlingEntity, HandlingDTO.class);
            UserDTO userDTO = modelMapper.map(handlingEntity.getUser(), UserDTO.class);
            userDTO.setDepartmentName(handlingEntity.getUser().getDepartment().getName());
            handlingDTO.setUserDTO(userDTO);
            handlingDTOList.add(handlingDTO);
        }
        baseDocumentDTO.setType(appointmentEntity.getBaseDocumentEntity().getCategory().getType());
        baseDocumentDTO.setHandlingDTO(handlingDTOList);

        appointmentDTO.setBaseDocumentDTO(baseDocumentDTO);

        return  appointmentDTO;
    }

    @Override
    public AppointmentEntity findEntityById(Long id) {
        AppointmentEntity appointmentEntity = appointmentRepository.findAppointmentEntityById(id);
        return appointmentEntity;
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
