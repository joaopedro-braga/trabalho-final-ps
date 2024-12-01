package com.depoisdosim.depoisdosim.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Task.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Task {
    public static final String TABLE_NAME = "task";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description", length = 100, nullable = false)
    private String description;
    
    @Column(name = "date", length = 100, nullable = false)
    private String date;

    @Column(name = "time", length = 100, nullable = false)
    private String time;

    @Column(name = "status", nullable = false)
    private String status = "Pendente";

    @ManyToOne
    @JoinColumn(name = "wedding_id", nullable = true)
    private Wedding wedding;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = true)
    private User supplier;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    
}
