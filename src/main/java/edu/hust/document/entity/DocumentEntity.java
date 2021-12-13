package edu.hust.document.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "document")
public class DocumentEntity {
    @Id
    @Column(name = "id")
    private String id;

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
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
