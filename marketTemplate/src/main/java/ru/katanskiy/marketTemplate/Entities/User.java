package ru.katanskiy.marketTemplate.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    @NotNull
    private String phone;

    @Column(name = "password")
    @NotNull
    @Size(min = 8)
    private String password;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 2, max = 15)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(String first_name, String last_name, String phone, String email, String password){
        this.firstName = first_name;
        this.lastName = last_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
}
