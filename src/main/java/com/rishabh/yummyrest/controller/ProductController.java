package com.rishabh.yummyrest.controller;


import com.rishabh.yummyrest.entity.Product;
import com.rishabh.yummyrest.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductRepo productRepo;

    @Autowired
    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepo.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(products);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") int productId) {
        Optional<Product> product = productRepo.findById(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/productRange")
    public ResponseEntity<List<Product>> getProductByRange() {
        List<Product> products = productRepo.findTop2ProductsByPriceRange(15, 30);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }


    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        if(productRepo.existsById(product.getProdID())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Product savedProduct = productRepo.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int productId, @RequestBody Product updatedProduct) {
        if(productRepo.existsById(productId)){
            updatedProduct.setProdID(productId);
            Product savedProduct = productRepo.save(updatedProduct);
            return ResponseEntity.ok(savedProduct);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int productId) {
        if(productRepo.existsById(productId)){
            productRepo.deleteById(productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/error")
    public ResponseEntity<String> triggerServerError() {
        try{
            throw new RuntimeException("Simulated server error");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An internal server error occurred");
        }
    }
}

