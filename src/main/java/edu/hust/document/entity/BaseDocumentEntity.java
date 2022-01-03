package edu.hust.document.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "base_document")
public class BaseDocumentEntity extends BaseEntity {
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "agency_code")
	private String agencyCode;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "signer_name")
	private String signerName;
	
	@Column(name = "signer_position")
	private String signerPosition;
	
	@Column(name = "issuance_time")
	private String issuanceTime;
	
	@Column(name = "forward_time")
	private String forwardTime;
	
	@Column(name = "other_info")
	private String otherInfo;

	@Column(name = "status")
	private String status;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

	@JsonManagedReference
	@OneToMany(mappedBy = "baseDocument")
	private Set<HandlingEntity> handlings;
	
	@OneToOne(mappedBy = "baseDocumentEntity")
	@JsonIgnore
    private DocumentEntity documentEntity;
	
	@OneToOne(mappedBy = "baseDocumentEntity")
	@JsonIgnore
    private AppointmentEntity appointmentEntity;
	
}
