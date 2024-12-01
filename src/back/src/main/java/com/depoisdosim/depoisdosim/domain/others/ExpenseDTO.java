package com.depoisdosim.depoisdosim.domain.others;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ExpenseDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Long wedding;
}
