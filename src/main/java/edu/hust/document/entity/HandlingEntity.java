package edu.hust.document.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "handling")
public class HandlingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "time")
	private LocalDateTime time;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "action")
	private String action;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
	private UserEntity user;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_document_id")
	private BaseDocumentEntity baseDocument;
}
