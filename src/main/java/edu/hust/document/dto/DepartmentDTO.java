package edu.hust.document.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.hust.document.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;

    private String code;

    private String name;

    private Long numberOfStaff;

    private String address;

    private UserDTO manager;

    private Set<UserDTO> users;

    private String phonenumber;
}
