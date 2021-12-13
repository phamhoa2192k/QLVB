package edu.hust.document.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.hust.document.api.output.UserOutput;
import edu.hust.document.dto.UserDTO;
import edu.hust.document.service.IUserService;

@CrossOrigin
@RestController
public class UserAPI {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping(value = "/api/user")
	public UserOutput getUsers() {
		UserOutput users = new UserOutput();
//		users.setPage(page);
//		Pageable pageable = new PageRequest(page - 1, limit);
//		users.setUsers(userService.findAll(pageable));
		users.setUsers(userService.findAll());
//		users.setTotalPage((int) Math.ceil((double) (userService.totalItem()) / limit));
		return users;
	}
	
	@PostMapping(value = "/api/user")
	public UserDTO createUser(@RequestBody UserDTO model) {
		return userService.save(model);
	}
	
	@PutMapping(value = "/api/user/{id}")
	public UserDTO updateUser(@RequestBody UserDTO model, @PathVariable("id") long id) {
		model.setId(id);
		return userService.save(model);
	}
	
	@DeleteMapping(value = "/api/user")
	public void deleteUser(@RequestBody long[] ids) {
		userService.delete(ids);
	}

}
