package ru.katanskiy.marketTemplate.Repositories.specifications;


import org.springframework.data.jpa.domain.Specification;
import ru.katanskiy.marketTemplate.Entities.Product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecification {

    public static Specification<Product> priceGreaterThan(int price){
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("coast"), price);
            }
        };
    }

    public static Specification<Product> priceLesserThan(int price){
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("coast"), price);
            }
        };
    }

    public static Specification<Product> categoryIdEquals(Long catId) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("category").get("id"), catId);
            }
        };
    }


}
