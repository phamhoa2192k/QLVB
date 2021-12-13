package edu.hust.document.service.impl;

import edu.hust.document.dto.DepartmentDTO;
import edu.hust.document.entity.DepartmentEntity;
import edu.hust.document.form.DepartmentForm;
import edu.hust.document.repository.DepartmentRepository;
import edu.hust.document.repository.UserRepository;
import edu.hust.document.service.IDepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DepartmentDTO> findAll() {
        List<DepartmentEntity> departmentEntityList = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        for (DepartmentEntity departmentEntity: departmentEntityList) {
            departmentDTOList.add(modelMapper.map(departmentEntity, DepartmentDTO.class));
        }
        return  departmentDTOList;
    }

    @Override
    public DepartmentDTO findById(Long id) {
        DepartmentEntity departmentEntity = departmentRepository.findDepartmentEntityById(id);
        if (departmentEntity == null){
            return null;
        }

        return  modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    @Override
    public List<DepartmentDTO> findByNameLike(String name) {
        List<DepartmentEntity> departmentEntityList = departmentRepository.findDepartmentEntitiesByNameLike(name);
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        for (DepartmentEntity departmentEntity: departmentEntityList) {
            departmentDTOList.add(modelMapper.map(departmentEntity, DepartmentDTO.class));
        }
        return  departmentDTOList;
    }

    @Override
    public DepartmentDTO insert(DepartmentForm departmentForm){
        DepartmentEntity departmentEntity = new DepartmentEntity();
        setDepartmentFormForEntity(departmentForm, departmentEntity);

        DepartmentEntity departmentEntity1;
        try {
            departmentEntity1 = departmentRepository.save(departmentEntity);
        }catch (Exception e) {
            return null;
        }


        return modelMapper.map(departmentEntity1, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO update(DepartmentForm departmentForm){
        DepartmentEntity departmentEntity = departmentRepository.findDepartmentEntityById(departmentForm.getId());
        setDepartmentFormForEntity(departmentForm, departmentEntity);
        departmentEntity.setId(departmentForm.getId());

        DepartmentEntity departmentEntity1;
        try {
            departmentEntity1 = departmentRepository.save(departmentEntity);
        }catch (Exception e) {
            return null;

        }

        return modelMapper.map(departmentEntity1, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO delete(Long id){
        DepartmentEntity departmentEntity = departmentRepository.findDepartmentEntityById(id);
        if (departmentEntity == null){
            return null;
        }

        try {
            departmentRepository.delete(departmentEntity);
        }catch (Exception e) {
            //Log
            return null;
        }

        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    void setDepartmentFormForEntity(DepartmentForm departmentForm, DepartmentEntity departmentEntity){
        departmentEntity.setAddress(departmentForm.getAddress());
        departmentEntity.setCode(departmentForm.getCode());
        departmentEntity.setName(departmentForm.getName());
        departmentEntity.setPhonenumber(departmentForm.getPhonenumber());
    }
}
