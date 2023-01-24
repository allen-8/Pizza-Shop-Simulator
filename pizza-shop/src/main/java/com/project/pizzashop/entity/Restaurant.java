package com.project.pizzashop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Restaurants")
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rest_id;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Restaurant name is mandatory")
    private String name;
    @Column(nullable = false)
    @NotBlank(message = "City is mandatory")
    private String city;
    @Column(nullable = false)
    @NotBlank(message = "Address is mandatory")
    private String address;
    @Column(nullable = false)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is invalid")
    private String email;
    @Pattern(message = "Phone format is invalid", regexp = "[\\+]?[0-9]*")
    private String phone;
    @Column(columnDefinition = "Double default '1.0'")
    @Min(message = "Rate value must be in range (1, 5)", value = 1)
    @Max(message = "Rate value must be in range (1, 5)", value = 5)
    private double rate;
    private String photo;
    public Restaurant() {
        this("", "", "", "", "", 5.5, "");
    }
    public Restaurant(String name, String city, String address, String email, String phone, double rate, String photo) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.rate = rate;
        this.photo = photo;
    }
}
