package edu.hust.document.mapper;

import edu.hust.document.dto.DepartmentDTO;
import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.DepartmentEntity;
import org.modelmapper.ModelMapper;

public class DepartmentMapper {


    public static DepartmentDTO departmentEntityToDTO(DepartmentEntity departmentEntity) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setAddress(departmentEntity.getAddress());
        departmentDTO.setCode(departmentEntity.getCode());
        departmentDTO.setId(departmentEntity.getId());
        departmentDTO.setName(departmentEntity.getName());
        departmentDTO.setPhonenumber(departmentEntity.getPhonenumber());
        departmentDTO.setNumberOfStaff(departmentEntity.getNumberOfStaff());

        ModelMapper modelMapper = new ModelMapper();
        return departmentDTO;

    }
}
