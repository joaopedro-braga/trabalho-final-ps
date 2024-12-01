package com.depoisdosim.depoisdosim.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Guest.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Guest {

    public static final String TABLE_NAME = "guest";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @Column(name = "email", length = 100, nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String email;

    @Column(name = "num_people", nullable = false)
    private int numPeople;

    @Column(name = "name_people", length = 400, nullable = true)
    private String namePeople;

    @ManyToOne
    @JoinColumn(name = "wedding_id", nullable = true)
    private Wedding wedding;
}
