package edu.hust.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlingDTO {
    private Long id;

    private LocalDateTime time;

    private String note;

    private String action;

    private Long handlingUserId;
    
    private Long documentId;

    private String handlingUserName;

}
