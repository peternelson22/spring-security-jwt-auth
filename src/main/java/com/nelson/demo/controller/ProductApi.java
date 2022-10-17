package com.nelson.demo.controller;

import com.nelson.demo.model.Product;
import com.nelson.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(name = "/products")
public class ProductApi {

    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Product> saveProducts (@Valid @RequestBody Product product){
        Product saveProduct = productRepository.save(product);
        URI productURI = URI.create("/products" + saveProduct.getId());

        return ResponseEntity.created(productURI).body(saveProduct);
    }

    @GetMapping
    public List<Product> productList(){
        return productRepository.findAll();
    }

}
