package com.Ecommerce_BE.Service;

import com.Ecommerce_BE.Dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {

    Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) throws IOException;

    Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) throws IOException;

    Response deleteProduct(Long productId);

    Response getProductById(Long productId);

    Response getAllProducts();

    Response getProductsByCategory(Long categoryId);

    Response searchProduct(String searchValue);
}
