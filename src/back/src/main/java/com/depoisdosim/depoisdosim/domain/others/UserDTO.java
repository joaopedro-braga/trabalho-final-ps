package com.depoisdosim.depoisdosim.domain.others;

import com.depoisdosim.depoisdosim.domain.user.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private String email;
    private Long wedding;
}
