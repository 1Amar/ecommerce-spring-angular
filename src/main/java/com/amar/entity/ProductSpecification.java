package com.amar.entity;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> nameContains(String name) {
        return (root, query, cb) ->
            name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> priceGreaterThanOrEqual(Double min) {
        return (root, query, cb) ->
            min == null ? null : cb.ge(root.get("price"), min);
    }

    public static Specification<Product> priceLessThanOrEqual(Double max) {
        return (root, query, cb) ->
            max == null ? null : cb.le(root.get("price"), max);
    }
}

