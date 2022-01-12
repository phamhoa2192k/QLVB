package edu.hust.document.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.UserEntity;
import edu.hust.document.service.IUserService;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserAPI {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping
	public List<UserEntity> getAllUsers() {
		return userService.findAll();
	}
	
	@GetMapping("/{userId}")
	public UserEntity getUserById(@PathVariable @Valid @NotNull @Min(1) Long userId) {
		return userService.findUserById(userId);
	}
	
	@PostMapping
	public UserEntity createUser(@RequestBody UserDTO userDTO) {
		return userService.createUser(userDTO);
	}
	
	@PutMapping(value = "/{userId}")
	public UserEntity updateUser(@RequestBody UserDTO userDTO, @PathVariable("userId") Long userId) {
		return userService.updateUser(userDTO, userId);
	}
	
	@DeleteMapping
	public void deleteUser(@RequestBody long[] ids) {
		userService.delete(ids);
	}
	
	@GetMapping("/all/{departmentId}")
	public List<UserEntity> getUsersByDepartmentId(@PathVariable @Valid @NotNull @Min(1) Long departmentId) {
		return userService.findUsersByDepartmentId(departmentId);
	}
	
	@GetMapping("/managers")
	public List<UserEntity> getAllManagers() {
		return userService.findAllManagers();
	}
}