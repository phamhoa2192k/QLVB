package edu.hust.document.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO extends BaseDocumentDTO{

    private LocalDateTime deadline;

    private String securityLevel;

    private String urgencyLevel;
}
