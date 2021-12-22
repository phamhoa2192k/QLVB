package edu.hust.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlingDTO {

    private Long id;

    private Timestamp time;

    private String note;

    private String action;

    private UserDTO userDTO;

}
