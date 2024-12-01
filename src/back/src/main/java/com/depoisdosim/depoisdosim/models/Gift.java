package com.depoisdosim.depoisdosim.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Gift.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Gift {
    public interface CreateGift {}
    public interface UpdateGift {}

    public static final String TABLE_NAME = "gift";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", length = 100, nullable = false, unique = false)
    @NotBlank(groups = {CreateGift.class, UpdateGift.class})
    @Size(groups = {CreateGift.class, UpdateGift.class}, min = 2, max = 100)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    @NotBlank(groups = {CreateGift.class, UpdateGift.class})
    @Size(groups = {CreateGift.class, UpdateGift.class}, min = 1, max = 255)
    private String description;

    @Column(name = "available")
    private Boolean available = true;

    @Column(name = "price", nullable = false)
    @DecimalMin(groups = {CreateGift.class, UpdateGift.class}, value = "0.0")
    private Double price;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "wedding_id", nullable = false)
    private Wedding wedding;

    @OneToOne(mappedBy = "gift")
    private GiftMessage giftMessage;
}
