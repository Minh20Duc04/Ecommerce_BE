package com.Ecommerce_BE.Repository;

import com.Ecommerce_BE.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long id);

    List<Product> findByNameContainingOrDescriptionContaining(String name, String description); //nghia la ko can go toan bo name hay description thi cung se tra ve product

}
