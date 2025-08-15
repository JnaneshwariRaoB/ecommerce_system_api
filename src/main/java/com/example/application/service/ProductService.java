package com.example.application.service;


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.application.entity.Product;
//import com.example.application.repository.ProductRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ProductService {
//    @Autowired
//    private ProductRepository productRepository;
//
//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
//
//    public Optional<Product> getProductById(Long id) {
//        return productRepository.findById(id);
//    }
//
//    public Product createProduct(Product product) {
//        return productRepository.save(product);
//    }
//
//    public Product updateProduct(Long id, Product productDetails) {
//        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
//        product.setName(productDetails.getName());
//        product.setDescription(productDetails.getDescription());
//        product.setPrice(productDetails.getPrice());
//        product.setStockQuantity(productDetails.getStockQuantity());
//        return productRepository.save(product);
//    }
//
//    public void deleteProduct(Long id) {
//        productRepository.deleteById(id);
//    }
//}





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.entity.Product;
import com.example.application.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger auditLogger = LoggerFactory.getLogger("auditLogger");
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        auditLogger.info("Admin action: Product created with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        Product updatedProduct = productRepository.save(product);
        auditLogger.info("Admin action: Product with ID {} was updated.", id);
        return updatedProduct;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        auditLogger.warn("Admin action: Product with ID {} was deleted.", id);
    }
}