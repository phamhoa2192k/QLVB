package edu.hust.document.service.impl;

import edu.hust.document.dto.AppointmentDTO;
import edu.hust.document.dto.BaseDocumentDTO;
import edu.hust.document.dto.HandlingDTO;
import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.AppointmentEntity;
import edu.hust.document.entity.BaseDocumentEntity;
import edu.hust.document.entity.CategoryEntity;
import edu.hust.document.entity.HandlingEntity;
import edu.hust.document.form.AppointmentForm;
import edu.hust.document.repository.AppointmentRepository;
import edu.hust.document.repository.BaseDocumentRepository;
import edu.hust.document.repository.CategoryRepository;
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
    private BaseDocumentRepository baseDocumentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AppointmentDTO> findAll() {
        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAll();
        return  setListDTO(appointmentEntityList);
    }

    @Override
    public List<AppointmentDTO> findLikeByName(String name) {
        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAppointmentEntitiesByNameLike(name);
        return  setListDTO(appointmentEntityList);
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
        BaseDocumentEntity baseDocumentEntity = new BaseDocumentEntity();
        setAppointmentFormForEntity(appointmentForm, appointmentEntity, baseDocumentEntity);

        baseDocumentEntity.setCreatedBy(appointmentForm.getCreated_by());
        baseDocumentEntity.setStatus("Tiếp nhận");
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        baseDocumentEntity.setCreatedDate(date);

        AppointmentEntity appointmentEntity1;
        BaseDocumentEntity baseDocumentEntity1;
        try {
            baseDocumentEntity1 = baseDocumentRepository.save(baseDocumentEntity);
            appointmentEntity.setId(baseDocumentEntity1.getId());
            appointmentEntity1 = appointmentRepository.save(appointmentEntity);
        }catch (Exception e) {
            return null;
        }

        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity1, BaseDocumentDTO.class);
        System.out.println(baseDocumentEntity1.getCategory().getType() + "123");
        baseDocumentDTO.setType(baseDocumentEntity1.getCategory().getType());
        AppointmentDTO appointmentDTO = modelMapper.map(appointmentEntity1, AppointmentDTO.class);
        appointmentDTO.setBaseDocumentDTO(baseDocumentDTO);

        return appointmentDTO;
    }

    @Override
    public AppointmentDTO update(AppointmentForm appointmentForm) {
        AppointmentEntity appointmentEntity = appointmentRepository.findAppointmentEntityById(appointmentForm.getId());
        if (appointmentEntity == null)  return null;
        BaseDocumentEntity baseDocumentEntity = appointmentEntity.getBaseDocumentEntity();
        setAppointmentFormForEntity(appointmentForm, appointmentEntity, baseDocumentEntity);

        baseDocumentEntity.setModifedBy(appointmentForm.getModifed_by());
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        baseDocumentEntity.setModifedDate(date);

        AppointmentEntity appointmentEntity1;
        BaseDocumentEntity baseDocumentEntity1;
        try {
            baseDocumentEntity1 = baseDocumentRepository.save(baseDocumentEntity);
            appointmentEntity1 = appointmentRepository.save(appointmentEntity);
        }catch (Exception e) {
            return null;
        }

        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity1, BaseDocumentDTO.class);
        System.out.println(baseDocumentEntity1.getCategory().getType() + "123");
        baseDocumentDTO.setType(baseDocumentEntity1.getCategory().getType());
        AppointmentDTO appointmentDTO = modelMapper.map(appointmentEntity1, AppointmentDTO.class);
        appointmentDTO.setBaseDocumentDTO(baseDocumentDTO);

        return appointmentDTO;
    }

    @Override
    public AppointmentDTO delete(Long id){
        AppointmentEntity appointmentEntity = appointmentRepository.findAppointmentEntityById(id);
        if (appointmentEntity == null){
            return null;
        }

        return modelMapper.map(appointmentEntity, AppointmentDTO.class);
    }

    void setAppointmentFormForEntity(AppointmentForm appointmentForm, AppointmentEntity appointmentEntity,
                                     BaseDocumentEntity baseDocumentEntity){
        appointmentEntity.setSecurityLevel(appointmentForm.getSecurityLevel());
        appointmentEntity.setUrgencyLevel(appointmentForm.getUrgencyLevel());

        baseDocumentEntity.setCode(appointmentForm.getCode());
        baseDocumentEntity.setName(appointmentForm.getName());
        baseDocumentEntity.setContent(appointmentForm.getContent());
        baseDocumentEntity.setAgencyCode(appointmentForm.getAgencyCode());
        baseDocumentEntity.setNumber(appointmentForm.getNumber());
        baseDocumentEntity.setSignerName(appointmentForm.getSignerName());
        baseDocumentEntity.setSignerPosition(appointmentForm.getSignerPosition());
        baseDocumentEntity.setIssuanceTime(appointmentForm.getIssuanceTime());
        baseDocumentEntity.setForwardTime(appointmentForm.getForwardTime());
        baseDocumentEntity.setOtherInfo(appointmentForm.getOtherInfo());

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByNameAndType("Giấy mời", "Giấy mời");
        System.out.println(categoryEntity.getType());

        baseDocumentEntity.setCategory(categoryEntity);
    }

    private List<AppointmentDTO> setListDTO(List<AppointmentEntity> appointmentEntityList){
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
}
