package edu.hust.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDocumentDTO extends BaseDTO{

    private String name;

    private String content;

    private String number;

    private String agencyCode;

    private String symbol;

    private Date issuanceTime;

    private String type;

    private String status;

    private String categoryCode;

    private Long assigneeId;

    private int numberOfPage;

    private String file;

}
