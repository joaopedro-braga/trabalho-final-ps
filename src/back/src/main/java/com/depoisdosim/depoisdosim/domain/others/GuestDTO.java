package com.depoisdosim.depoisdosim.domain.others;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class GuestDTO {
    private Long id;
    private String name;
    private String email;
    private int numPeople;
    private String namePeople;
    private Long wedding;
}
