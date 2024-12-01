package com.depoisdosim.depoisdosim.domain.others;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeddingDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private String time;
    private String local;
    private Long budget;
    private Boolean budgetExceeded;
    private Boolean finished;
}
