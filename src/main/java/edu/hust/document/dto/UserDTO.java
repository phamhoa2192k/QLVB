package edu.hust.document.dto;


import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UserDTO extends BaseDTO {

	private String userName;
	private String password;
	private String fullName;
	private Integer status;
	private Integer gender;
	private String dob;
	private String position;
	private String departmentCode;
	private List<String> roleCodes;
}
