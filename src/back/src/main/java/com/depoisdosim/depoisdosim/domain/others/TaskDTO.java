package com.depoisdosim.depoisdosim.domain.others;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String date;
    private String time;
    private String status;
    private Long user;
    private Long supplier = null;
}
