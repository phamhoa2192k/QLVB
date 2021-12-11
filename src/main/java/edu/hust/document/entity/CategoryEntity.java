package edu.hust.document.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	private Set<BaseDocumentEntity> baseDocuments;
}
