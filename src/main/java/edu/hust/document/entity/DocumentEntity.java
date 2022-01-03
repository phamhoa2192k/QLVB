package edu.hust.document.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @JoinColumn(name = "id")
	BaseDocumentEntity baseDocumentEntity;

    @Column(name = "deadline")
    private Timestamp deadline;

    @Column(name = "attached_document")
    private String attachedDocument;

    @Column(name = "security_level")
    private String securityLevel;

    @Column(name = "urgency_level")
    private String urgencyLevel;


}
