package com.Ecommerce_BE.Service.ServiceImp;

import com.Ecommerce_BE.Dto.ProductDto;
import com.Ecommerce_BE.Dto.Response;
import com.Ecommerce_BE.Exception.NotFoundException;
import com.Ecommerce_BE.Mapper.EntityDtoMapper;
import com.Ecommerce_BE.Model.Category;
import com.Ecommerce_BE.Model.Product;
import com.Ecommerce_BE.Repository.CategoryRepository;
import com.Ecommerce_BE.Repository.ProductRepository;
import com.Ecommerce_BE.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service

public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final FileStorageService fileStorageService;


    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) throws IOException {

        // Kiểm tra giá trị đầu vào
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name must not be empty");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Product image must not be empty");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        String productImageUrl = fileStorageService.saveFile(image);

        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(productImageUrl);

        log.info("Creating product: name={}, description={}, price={}, categoryId={}", name, description, price, categoryId);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            log.error("Error saving product: {}", e.getMessage());
            return Response.builder()
                    .status(500)
                    .message("An error occurred while saving the product")
                    .build();
        }

        return Response.builder()
                .status(200)
                .message("Product successfully created")
                .build();
    }


    @Override
    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) throws IOException {

        Product product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException("Product Not Found"));
        if(categoryId != null)
        {
            Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
            product.setCategory(category);
        }
        if(name != null)
        {
            product.setName(name);
        }
        if(description != null)
        {
            product.setDescription(description);
        }
        if(price != null)
        {
            product.setPrice(price);
        }
        if(image != null && !image.isEmpty())
        {
            product.setImageUrl(fileStorageService.saveFile(image));
        }

        productRepository.save(product);

        return Response.builder()
                .status(200)
                .message("Product updated successfully ")
                .build();
    }

    @Override
    public Response deleteProduct(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException("Product Not Found"));
        productRepository.delete(product);

        return Response.builder()
                .status(200)
                .message("Product deleted successfully")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException("Product Not Found"));
        ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);

        return Response.builder()
                .status(200)
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<ProductDto> productDtoList = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }

    @Override
    public Response getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));

        List<Product> products = productRepository.findByCategoryId(categoryId);
        if(products.isEmpty())
        {
            throw new NotFoundException("No Products found for this category");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }

    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(searchValue, searchValue);
        if (products.isEmpty()){
            throw new NotFoundException("No Products Found");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }
}
