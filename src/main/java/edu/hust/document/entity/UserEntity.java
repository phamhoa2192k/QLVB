package edu.hust.document.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "status")
	private Integer status;

	@Column(name = "gender")
	private Integer gender;

	@Column(name = "dob")
	private Date dob;

	@Column(name = "position")
	private String position;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<HandlingEntity> handlings;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private DepartmentEntity department;

	@OneToOne(mappedBy = "assignee")
	@JsonIgnore
	private BaseDocumentEntity baseDocumentEntity;
}
