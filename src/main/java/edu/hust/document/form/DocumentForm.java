package edu.hust.document.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentForm {
    private Long id;

    private LocalDateTime deadline;

    private String securityLevel;

    private String urgencyLevel;

    private String created_by;

    private String modifed_by;

    private String name;

    private String content;

    private String number;

    private String agencyCode;

    private String numberOfPage;

    private String symbol;

    private Date issuanceTime;

    private String file;

    private Long categoryId;
}
