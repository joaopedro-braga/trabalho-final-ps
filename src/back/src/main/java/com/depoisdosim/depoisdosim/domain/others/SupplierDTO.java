package com.depoisdosim.depoisdosim.domain.others;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SupplierDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
}
