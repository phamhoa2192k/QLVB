package edu.hust.document.dto;

import edu.hust.document.entity.BaseDocumentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO extends BaseDTO{

    BaseDocumentEntity baseDocumentEntity;

    private String securityLevel;

    private String urgencyLevel;
}
