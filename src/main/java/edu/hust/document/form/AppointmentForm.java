package edu.hust.document.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentForm {
    private Long id;

    private String securityLevel;

    private String urgencyLevel;

    private String created_by;

    private String modifed_by;

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

    private Long category_id;

}
