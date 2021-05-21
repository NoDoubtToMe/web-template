package ru.katanskiy.marketTemplate.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.katanskiy.marketTemplate.Entities.Product;
import ru.katanskiy.marketTemplate.services.ProductService;
import ru.katanskiy.marketTemplate.utils.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private Cart cart;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cart", cart);
        return "cart_page";
    }

    @GetMapping("add/{id}")
    public void addItemToCart(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = productService.findById(id);
        cart.add(product);
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("remove/{id}")
    public String removeProductFromCart(@PathVariable Long id) {
        cart.removeByProductId(id);
        return "redirect:/cart";

    }


}
