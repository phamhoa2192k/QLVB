package edu.hust.document.dto;

import edu.hust.document.entity.BaseDocumentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {

    private String id;

    private BaseDocumentDTO baseDocumentDTO;

    private Timestamp deadline;

    private String attachedDocument;

    private String securityLevel;

    private String urgencyLevel;
}
