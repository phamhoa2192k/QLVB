package edu.hust.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;

    private String code;

    private String name;

    private Long numberOfStaff;

    private String address;

    private String phonenumber;
}
