package edu.hust.document.api.output;

import java.util.ArrayList;
import java.util.List;

import edu.hust.document.dto.UserDTO;

public class UserOutput {
	
	private int page;
	private int totalPage;
	private List<UserDTO> users = new ArrayList<>();
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<UserDTO> getUsers() {
		return users;
	}
	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
	
}
