package com.depoisdosim.depoisdosim.domain.others;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiftMessageDTO {
    private Long id;
    private String name;
    private String email;
    private String description;
    private String giftName;
    private Long weddingId;
}
