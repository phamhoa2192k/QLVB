package edu.hust.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDocumentDTO extends BaseDTO{

    private String name;

    private String content;

    private String number;

    private String agencyCode;

    private String symbol;

    private String issuanceTime;

    private String type;

    private String status;

    private List<HandlingDTO> handlingDTO;

    private String categoryCode;

    private Long assigneeId;

    private int numberOfPage;

    private String file;

}
