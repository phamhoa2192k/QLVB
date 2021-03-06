package edu.hust.document.controller;

import com.google.gson.Gson;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.UserEntity;
import edu.hust.document.repository.UserRepository;

@RestController
public class APIController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping(path = "/api/test")
	public String test(){
		UserEntity user = userRepository.findUserByUserName("phamhoa@gmail.com");
		ModelMapper mapper = new ModelMapper();
		UserDTO userDTO = mapper.map(user, UserDTO.class);
		return new Gson().toJson(userDTO);
	}

	@GetMapping(path = "/api/currentuser")
	public UserDTO getCurrentUser(Authentication authentication){
		String username = authentication.getName();
		UserEntity u = userRepository.findUserByUserName(username);
		ModelMapper mapper = new ModelMapper();
		UserDTO userDTO = mapper.map(u, UserDTO.class);
		return userDTO;
	}
}
