package ru.katanskiy.marketTemplate.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.katanskiy.marketTemplate.Entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
