package edu.hust.document.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDTO {
	
	private Long id;
	private Date createdDate;
	private Date modifedDate;
	private String createdBy;
	private String modifedBy;
	
	
}
