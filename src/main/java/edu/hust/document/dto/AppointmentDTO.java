package edu.hust.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO{

    private Long id;

    BaseDocumentDTO baseDocumentDTO;

    private String securityLevel;

    private String urgencyLevel;
}
