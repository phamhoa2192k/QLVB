package edu.hust.document.dto;

import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class UserDTO extends BaseDTO {
    private String userName;
    private Set<RoleDTO> roles;
}
