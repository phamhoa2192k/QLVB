package edu.hust.document.service;

import java.util.List;

import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.UserEntity;

public interface IUserService {
	public UserEntity createUser(UserDTO userDTO);
	public UserEntity updateUser(UserDTO userDTO, Long userId);
	public void delete(long[] ids);
	public List<UserEntity> findAll();
	public UserEntity findUserById(Long id);
	public List<UserEntity> findUsersByDepartmentId(Long id);
	public List<UserEntity> findAllManagers();
}
