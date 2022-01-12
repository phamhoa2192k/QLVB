package edu.hust.document.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "document")
public class DocumentEntity {
	
	@Id
    private Long id;

	@OneToOne
    @JoinColumn(name = "base_document_id")
	private BaseDocumentEntity baseDocumentEntity;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "security_level")
    private String securityLevel;

    @Column(name = "urgency_level")
    private String urgencyLevel;


}
