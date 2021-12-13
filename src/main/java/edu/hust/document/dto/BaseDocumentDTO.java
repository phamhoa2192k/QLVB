package edu.hust.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDocumentDTO extends BaseDTO{

    private String code;

    private String name;

    private String content;

    private String agencyCode;

    private String number;

    private String signerName;

    private String signerPosition;

    private String issuanceTime;

    private String forwardTime;

    private String otherInfo;

    private String type;

}
