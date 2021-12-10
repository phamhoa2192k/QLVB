package edu.hust.document.model;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "user_id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "gender")
	private Boolean gender;
	
	@Column(name = "dateofbirth")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "office")
	private String office;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Department department;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;
}
