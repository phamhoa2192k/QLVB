package edu.hust.document.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import edu.hust.document.dto.UserDTO;

public interface IUserService {
	public UserDTO save(UserDTO userDTO);
	public void delete(long[] ids);
	public List<UserDTO> findAll();
	public List<UserDTO> findAll(Pageable pageable);
	public int totalItem();
}
