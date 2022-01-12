package edu.hust.document.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentForm {
    private Long id;

    private String securityLevel;

    private String urgencyLevel;

    private String created_by;

    private String modifed_by;

    private String symbol;

    private String name;

    private String content;

    private String agencyCode;

    private String number;

    private Integer numberOfPage;

    private Date issuanceTime;

    private String file;

    private Long categoryId;


}
