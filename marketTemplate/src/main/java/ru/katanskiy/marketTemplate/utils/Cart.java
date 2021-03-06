package ru.katanskiy.marketTemplate.utils;


import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.katanskiy.marketTemplate.Entities.OrderItem;
import ru.katanskiy.marketTemplate.Entities.Product;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class Cart {
    private List<OrderItem> items;
    private BigDecimal cartPrice;


    @PostConstruct
    public  void init(){
        items = new ArrayList<>();
    }

    public void clear(){
        items.clear();
        recalculate();
    }

    public void add(Product product){
        for(OrderItem element : items){
            if(element.getProduct().getId().equals(product.getId())){
                element.setQuantity(element.getQuantity()+1);
                element.setPrice(new BigDecimal(element.getQuantity()*element.getProduct().getCoast().doubleValue()));
                recalculate();
                return;
            }
        }
        items.add(new OrderItem(product));
        recalculate();
    }

    public void removeByProductId(Long productId){
        for(int i=0; i<items.size(); i++){
            if(items.get(i).getProduct().getId().equals(productId)){
                items.remove(i);
                recalculate();
                return;
            }
        }
    }

    public void recalculate(){
        cartPrice = new BigDecimal(0.0);
        for(OrderItem i : items){
            cartPrice = cartPrice.add(i.getPrice());
        }
    }

}
