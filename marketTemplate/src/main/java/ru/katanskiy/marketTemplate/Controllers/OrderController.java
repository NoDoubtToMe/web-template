package ru.katanskiy.marketTemplate.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.katanskiy.marketTemplate.Entities.Order;
import ru.katanskiy.marketTemplate.Entities.User;
import ru.katanskiy.marketTemplate.services.OrderService;
import ru.katanskiy.marketTemplate.services.UserService;
import ru.katanskiy.marketTemplate.utils.Cart;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private Cart cart;

    @GetMapping("/info")
    public String showOrderInfo(Model model, Principal principal){
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("cart", cart);
        model.addAttribute("def_phone", user.getPhone());
        return "order_info_before_confirmation";
    }



    @PostMapping("/create")
    public String createOrder(Model model, Principal principal,
                              @RequestParam(name = "address") String address,
                              @RequestParam(name = "phone_number") String phone ) {

        User user = userService.findByPhone(principal.getName());
        Order order = new Order(user, cart, address, phone );
        order = orderService.save(order);
        model.addAttribute("order_id_str", String.format("%05d", order.getId()));
        return "order_confirmation";
    }

    @GetMapping("/history")
    public String showHistory(Model model, Principal principal ) {
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("orders", user.getOrders());
        return "order_history";
    }
}
