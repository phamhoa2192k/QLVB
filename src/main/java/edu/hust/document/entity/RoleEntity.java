package edu.hust.document.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "code")
	private String code;

	@JsonBackReference
	@ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;
}
