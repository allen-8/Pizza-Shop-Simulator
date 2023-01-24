package com.project.pizzashop.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name="Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;
    @Column(nullable = false)
    @NotBlank(message = "User name is mandatory")
    private String name;
    private String surname;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is invalid")
    private String email;
    @Column(columnDefinition = "Boolean default 'false'")
    private Boolean isActive;
    @Column(nullable = false)
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6)
    private String password;
}
