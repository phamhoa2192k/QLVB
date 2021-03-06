package edu.hust.document.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "created_date")
	@CreatedDate
	@JsonIgnore
	private Date createdDate;
	
	@Column(name = "modifed_date")
	@LastModifiedDate
	@JsonIgnore
	private Date modifedDate;
	
	@Column(name = "created_by")
	@CreatedBy
	@JsonIgnore
	private String createdBy;
	
	@Column(name = "modifed_by")
	@LastModifiedBy
	@JsonIgnore
	private String modifedBy;
}
