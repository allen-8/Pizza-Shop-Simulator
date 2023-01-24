package com.project.pizzashop.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id;
    @ManyToOne()
    @JoinColumn(name = "pizza_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Pizza pizza;
    @Column(columnDefinition = "Varchar(20) default 'SMALL'")
    String size;
    double price;
    public Order(Pizza pizza, String size, Double price) {
        this.pizza = pizza;
        this.size = size;
        this.price = price;
    }
}
