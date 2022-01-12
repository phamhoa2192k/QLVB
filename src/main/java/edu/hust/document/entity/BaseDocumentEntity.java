package edu.hust.document.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "base_document")
public class BaseDocumentEntity extends BaseEntity {
	
	@Column(name = "name")
	private String name;

	@Column(name = "content")
	private String content;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "agency_code")
	private String agencyCode;
	
	@Column(name = "symbol")
	private String symbol;
	
	@Column(name = "issuance_time")
	private Date issuanceTime;

	@Column(name = "status")
	private String status;
	
	@Column(name = "number_of_page")
	private String numberOfPage;
	
	@Column(name = "file", columnDefinition = "TEXT")
	private String file;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private CategoryEntity category;

	@OneToMany(mappedBy = "baseDocument")
	@JsonIgnore
	private Set<HandlingEntity> handlings;

	@OneToOne(mappedBy = "baseDocumentEntity")
	@JsonIgnore
	private DocumentEntity documentEntity;

	@OneToOne(mappedBy = "baseDocumentEntity")
	@JsonIgnore
	private AppointmentEntity appointmentEntity;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity assignee;
	
	@OneToOne
	@JoinColumn(name = "user_id")
    private UserEntity assignee;
	
}
