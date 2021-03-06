package edu.hust.document.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentForm {
    private Long id;
    private String address;
    private String code;
    private String name;
    private String phonenumber;
}
