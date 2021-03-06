package edu.hust.document.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserEntity createUser(UserDTO userDTO) {
		UserEntity userEntity = userMapper.toEntity(userDTO);
		
		DepartmentEntity departmentEntity = departmentRepository.findByCode(userDTO.getDepartmentCode()).get(0);
		userEntity.setDepartment(departmentEntity);
		
		Set<RoleEntity> roleEntities = new HashSet<RoleEntity>();
		for (String roleCode: userDTO.getRoleCodes()) {
			RoleEntity roleEntity = roleRepository.findRoleByCode(roleCode);
			roleEntities.add(roleEntity);
		}
		userEntity.setRoles(roleEntities);
		userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		
		return userRepository.save(userEntity);	
	}
	
	@Override
	public UserEntity updateUser(UserDTO userDTO, Long userId) {
		DepartmentEntity departmentEntity = departmentRepository.findByCode(userDTO.getDepartmentCode()).get(0);
		Set<RoleEntity> roleEntities = new HashSet<RoleEntity>();
		for (String roleCode: userDTO.getRoleCodes()) {
			RoleEntity roleEntity = roleRepository.findRoleByCode(roleCode);
			roleEntities.add(roleEntity);
		}
		
		UserEntity userEntity = userRepository.findById(userId)
				.orElseThrow(() -> {
					return new ResourceNotFoundException(userId, UserEntity.class);
				});
		
		userEntity = userMapper.toEntity(userEntity, userDTO);
		userEntity.setDepartment(departmentEntity);
		userEntity.setRoles(roleEntities);
		userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		
		return userRepository.save(userEntity);
				
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

	@Override
	public List<UserEntity> findUsersByDepartmentId(Long id) {
		return userRepository.findAllByDepartmentId(id);
	}

	@Override
	public List<UserEntity> findAllManagers() {
		return userRepository.findAllManagers();
	}

}
