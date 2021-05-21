package ru.katanskiy.marketTemplate.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.katanskiy.marketTemplate.Entities.Product;
import ru.katanskiy.marketTemplate.Repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Page<Product> findAll(Specification<Product> specification, Pageable pageable){
        return productRepository.findAll(specification, pageable);
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public Product findById(Long id){
        return productRepository.findById(id).get();
    }

    public Product save(Product product){
        return productRepository.save(product);
    }
}
