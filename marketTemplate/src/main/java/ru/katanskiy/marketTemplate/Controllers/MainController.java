package ru.katanskiy.marketTemplate.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.katanskiy.marketTemplate.Entities.Category;
import ru.katanskiy.marketTemplate.Entities.Order;
import ru.katanskiy.marketTemplate.Entities.Product;
import ru.katanskiy.marketTemplate.Entities.User;
import ru.katanskiy.marketTemplate.services.CategoryService;
import ru.katanskiy.marketTemplate.services.OrderService;
import ru.katanskiy.marketTemplate.services.ProductService;
import ru.katanskiy.marketTemplate.services.UserService;
import ru.katanskiy.marketTemplate.utils.Cart;
import ru.katanskiy.marketTemplate.utils.ProductFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private Cart cart;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String loginPage(Model model){
        return "login_page";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/";
        }
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam Map<String,String> params) {
        int pageIndex = 0;
        if(params.containsKey("p")){
            pageIndex = Integer.parseInt(params.get("p") )-1;
        }
        Pageable pageable = PageRequest.of(pageIndex, 5);
        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> page = productService.findAll(productFilter.getSpec(), pageable);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("productFiler", productFilter.getFilterDefinition());
        model.addAttribute("categories", categories);
        model.addAttribute("page", page);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable Long id) {
        Product product = productService.findById(id);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "edit_product";
    }

    @PostMapping("/edit")
    public String saveProduct(@ModelAttribute(name = "product") Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @PostMapping("/autorization")
    public String autorization(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "autorizationPage";
    }

    @GetMapping("/autorize_confirm")
    public String autorizeConfirm(@Valid @ModelAttribute(name = "user") User user,
                                  BindingResult bindingResult,
                                  @RequestParam Map<String, String> params){

        if (bindingResult.hasErrors()) {
            System.out.println("Error in validation!!!!");
            System.out.println("Error in validation!!!!");
            return "autorizationPage";
        }

        String bcryptPass = bCryptPasswordEncoder.encode(params.get("password"));
        User saveUser = new User(params.get("firstName"), params.get("lastName"),params.get("phone"),params.get("email"),bcryptPass);
        userService.saveUser(saveUser);
        System.out.println("user saved....");
        return "autorizeConfirm";
    }


 //  @PutMapping("/update")
 //  public Product product(@RequestBody Product product){
 //      return productService.save(product);
 //  }


 //  @GetMapping("/products")
 //  @ResponseBody
 //  public List<Product> getProducts(){
 //      return productService.findAll();
 //  }

}
