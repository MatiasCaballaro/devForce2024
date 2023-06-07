package com.devforce.liceman.usuario.infrastructure.dto;

import com.devforce.liceman.usuario.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private Role role;
  private String phone;
  private Boolean hasTeams;

}
