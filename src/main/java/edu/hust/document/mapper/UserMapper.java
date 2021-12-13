package edu.hust.document.mapper;

import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.UserEntity;

public class UserMapper {
    public static UserDTO userEntityToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setCreatedBy(userEntity.getCreatedBy());
        userDTO.setCreatedDate(userEntity.getCreatedDate());
        userDTO.setModifedBy(userEntity.getModifedBy());
        userDTO.setModifedDate(userEntity.getModifedDate());

        return userDTO;

    }
}
