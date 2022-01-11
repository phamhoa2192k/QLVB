package edu.hust.document.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String code;
	
	@Column(name = "type")
	private String type;

	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private Set<BaseDocumentEntity> baseDocuments;
}
