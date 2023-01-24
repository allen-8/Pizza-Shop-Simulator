package com.project.pizzashop.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name="Pizzas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pizza_id;
    @Column(nullable = false)
    @NotBlank(message = "Pizza name is mandatory")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;
    @Column(columnDefinition = "Varchar default 'No description'")
    private String description;
    @Column(columnDefinition = "Double default '-1'")
    private Double small_price;
    @Column(columnDefinition = "Double default '-1'")
    private Double medium_price;
    @Column(columnDefinition = "Double default '-1'")
    private Double big_price;
    private String photo;
    public Pizza(String name, Restaurant rest, String description, double small_price, double medium_price,
                 double big_price, String photo) {
        this.name = name;
        this.restaurant = rest;
        this.description = description;
        if (Objects.equals(description, ""))
            this.description = "-";
        this.small_price = small_price;
        this.medium_price = medium_price;
        this.big_price = big_price;
        this.photo = photo;
    }
}
