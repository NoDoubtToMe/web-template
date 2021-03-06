package ru.katanskiy.marketTemplate.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.katanskiy.marketTemplate.utils.Cart;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phone;

    public Order(User user, Cart cart, String address, String phone){
        this.user = user;
        this.price = cart.getCartPrice();
        this.items = new ArrayList<>();
        this.address = address;
        this.phone = phone;
        for(OrderItem i: items){
            i.setOrder(this);
            this.items.add(i);
        }
        cart.clear();
    }
}
