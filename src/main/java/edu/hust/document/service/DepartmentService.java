package edu.hust.document.service;

import edu.hust.document.dto.DepartmentDTO;
import edu.hust.document.entity.DepartmentEntity;
import edu.hust.document.entity.UserEntity;
import edu.hust.document.form.DepartmentForm;
import edu.hust.document.mapper.DepartmentMapper;
import edu.hust.document.repository.DepartmentRepository;
import edu.hust.document.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<DepartmentDTO> findAll() {
        List<DepartmentEntity> departmentEntityList = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        for (DepartmentEntity departmentEntity: departmentEntityList) {
            departmentDTOList.add(DepartmentMapper.departmentEntityToDTO(departmentEntity));
        }
        return  departmentDTOList;
    }

    public List<DepartmentDTO> findByNameLike(String name) {
        List<DepartmentEntity> departmentEntityList = departmentRepository.findDepartmentEntitiesByNameLike(name);
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        for (DepartmentEntity departmentEntity: departmentEntityList) {
            departmentDTOList.add(DepartmentMapper.departmentEntityToDTO(departmentEntity));
        }
        return  departmentDTOList;
    }

    public DepartmentEntity insert(DepartmentForm departmentForm){
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setAddress(departmentForm.getAddress());
        departmentEntity.setCode(departmentForm.getCode());
        departmentEntity.setName(departmentForm.getName());
        departmentEntity.setPhonenumber(departmentForm.getPhonenumber());
        departmentEntity.setNumberOfStaff(1L);
        UserEntity user = userRepository.findUserEntityById(departmentForm.getManager_id());
        departmentEntity.setManager(user);

        Set<UserEntity> userEntitySet = new HashSet<UserEntity>();
        userEntitySet.add(user);
        departmentEntity.setUsers(userEntitySet);

        DepartmentEntity departmentEntity1 = null;
        try {
            departmentEntity1 = departmentRepository.save(departmentEntity);
        }catch (Exception e) {
            //Log

        }

        return departmentEntity1;
    }

    public DepartmentEntity update(DepartmentForm departmentForm){
        DepartmentEntity departmentEntity = departmentRepository.findDepartmentEntityById(departmentForm.getId());
        departmentEntity.setId(departmentForm.getId());
        departmentEntity.setAddress(departmentForm.getAddress());
        departmentEntity.setCode(departmentForm.getCode());
        departmentEntity.setName(departmentForm.getName());
        departmentEntity.setPhonenumber(departmentForm.getPhonenumber());

        DepartmentEntity departmentEntity1 = null;
        try {
            departmentEntity1 = departmentRepository.save(departmentEntity);
        }catch (Exception e) {
            //Log

        }

        return departmentEntity1;
    }

    public DepartmentEntity delete(Long id){
        DepartmentEntity departmentEntity = departmentRepository.findDepartmentEntityById(id);

        if (departmentEntity == null){
            return null;
        }

        UserEntity user = departmentEntity.getManager();
        user.setDepartment(null);
        user = userRepository.save(user);
        departmentRepository.delete(departmentEntity);

        return departmentEntity;
    }
}
