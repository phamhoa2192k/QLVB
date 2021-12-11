package edu.hust.document.DTO;

import java.util.Set;

import org.springframework.stereotype.Component;

import edu.hust.document.entity.*;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class UserDTO extends BaseDTO {
  private String userName;
	private Set<RoleDTO> roles;
}
