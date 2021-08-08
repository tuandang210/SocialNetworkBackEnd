package com.codegym.service.product;

import com.codegym.model.Product;
import java.util.Optional;

public interface IProductService {

    Iterable<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void deleteById(Long id);

    Iterable<Product> findAll(int page, int size);
}
