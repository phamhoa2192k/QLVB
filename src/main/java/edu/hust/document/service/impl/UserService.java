package edu.hust.document.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.DepartmentEntity;
import edu.hust.document.entity.RoleEntity;
import edu.hust.document.entity.UserEntity;
import edu.hust.document.exception.ResourceNotFoundException;
import edu.hust.document.mapper.UserMapper;
import edu.hust.document.repository.DepartmentRepository;
import edu.hust.document.repository.RoleRepository;
import edu.hust.document.repository.UserRepository;
import edu.hust.document.service.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserEntity createUser(UserDTO userDTO) {
		UserEntity userEntity = userMapper.toEntity(userDTO);
		
		DepartmentEntity departmentEntity = departmentRepository.findDepartmentByCode(userDTO.getDepartmentCode());
		userEntity.setDepartment(departmentEntity);
		
		Set<RoleEntity> roleEntities = new HashSet<RoleEntity>();
		for (String roleCode: userDTO.getRoleCodes()) {
			RoleEntity roleEntity = roleRepository.findRoleByCode(roleCode);
			roleEntities.add(roleEntity);
		}
		userEntity.setRoles(roleEntities);
		
		return userRepository.save(userEntity);	
	}
	
	@Override
	public UserEntity updateUser(UserDTO userDTO, Long userId) {
		DepartmentEntity departmentEntity = departmentRepository.findDepartmentByCode(userDTO.getDepartmentCode());

		
		Set<RoleEntity> roleEntities = new HashSet<RoleEntity>();
		for (String roleCode: userDTO.getRoleCodes()) {
			RoleEntity roleEntity = roleRepository.findRoleByCode(roleCode);
			roleEntities.add(roleEntity);
		}
		
		return userRepository.findById(userDTO.getId())
				.map(oldUser -> {
					oldUser = userMapper.toEntity(userDTO);
					oldUser.setDepartment(departmentEntity);
					oldUser.getRoles().clear();
					oldUser.setRoles(roleEntities);
					
					return userRepository.save(oldUser);
				})
				.orElseThrow(() -> {
					return new ResourceNotFoundException(userId, UserEntity.class);
				});
	}
	

	@Override
	public void delete(long[] ids) {
		for(long item: ids) {
			userRepository.deleteById(item);
		}
	}

	@Override
	public List<UserEntity> findAll() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> {
					return new ResourceNotFoundException(id, UserEntity.class);
				});
	}

}