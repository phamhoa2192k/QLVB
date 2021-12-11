package edu.hust.document.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@Entity
@Table(name = "department")
public class DepartmentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id")
	private Long id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "numberofstaff")
	private Long numberOfStaff;
	
	@Column(name = "address")
	private String address;
	
	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(
		name = "manager_id", unique = true, updatable = true, nullable = true
	)
	private UserEntity manager;

	@OneToMany(mappedBy = "department")
	private Set<UserEntity> users;

	@Column(name = "phonenumber")
	private String phonenumber;

}
