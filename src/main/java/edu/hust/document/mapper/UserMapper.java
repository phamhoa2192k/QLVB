package edu.hust.document.mapper;

import java.sql.Date;

import org.springframework.stereotype.Component;

import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.UserEntity;

@Component
public class UserMapper {
	
	public UserEntity toEntity(UserDTO userDTO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(userDTO.getUserName());
		userEntity.setPassword(userDTO.getPassword());
		userEntity.setFullName(userDTO.getFullName());
		userEntity.setStatus(userDTO.getStatus());
		userEntity.setGender(userDTO.getGender());
		userEntity.setDob(Date.valueOf(userDTO.getDob()));
		userEntity.setPosition(userDTO.getPosition());
		return userEntity;
	}
	
	public UserEntity toEntity(UserEntity userEntity, UserDTO userDTO) {
		userEntity.setUserName(userDTO.getUserName());
		userEntity.setPassword(userDTO.getPassword());
		userEntity.setFullName(userDTO.getFullName());
		userEntity.setStatus(userDTO.getStatus());
		userEntity.setGender(userDTO.getGender());
		userEntity.setDob(Date.valueOf(userDTO.getDob()));
		userEntity.setPosition(userDTO.getPosition());
		return userEntity;
	}

}
