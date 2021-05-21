package ru.katanskiy.marketTemplate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.katanskiy.marketTemplate.Entities.Order;
import ru.katanskiy.marketTemplate.Repositories.OrderRepository;

@Service
public class OrderService{
    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order){
        return orderRepository.save(order);
    }
}
