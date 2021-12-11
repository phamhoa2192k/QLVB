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
	private ModelMapper modelMapper;

	@Override
	public UserDTO save(UserDTO userDTO) {
		UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
		
		Set<RoleEntity> roleEntities = new HashSet<RoleEntity>();
		for (String roleCode: userDTO.getRoleCodes()) {
			RoleEntity roleEntity = roleRepository.findRoleByCode(roleCode);
			roleEntities.add(roleEntity);
		}
		userEntity.setRoles(roleEntities);
		
		DepartmentEntity departmentEntity = departmentRepository.findDepartmentByCode(userDTO.getDepartmentCode());
		userEntity.setDepartment(departmentEntity);
		
		userEntity = userRepository.save(userEntity);
		
		return modelMapper.map(userEntity, UserDTO.class);
	}

	@Override
	public void delete(long[] ids) {
		for(long item: ids) {
			userRepository.deleteById(item);
		}
	}

	@Override
	public List<UserDTO> findAll(Pageable pageable) {
		List<UserDTO> users = new ArrayList<>();
		List<UserEntity> entities = userRepository.findAll(pageable).getContent();
		for (UserEntity user: entities) {
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			users.add(userDTO);
		}
		return users;
	}

	@Override
	public int totalItem() {
		return (int) userRepository.count();
	}

	@Override
	public List<UserDTO> findAll() {
		List<UserDTO> users = new ArrayList<>();
		List<UserEntity> entities = userRepository.findAll();
		for (UserEntity user: entities) {
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			users.add(userDTO);
		}
		return users;
	}

}
