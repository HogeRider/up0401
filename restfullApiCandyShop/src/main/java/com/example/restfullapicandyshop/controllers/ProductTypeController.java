package com.example.restfullapicandyshop.controllers;

import com.example.restfullapicandyshop.models.ProductType;
import com.example.restfullapicandyshop.repositories.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-types")
public class ProductTypeController {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping
    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductType> getProductTypeById(@PathVariable(value = "id") Integer id) {
        Optional<ProductType> productTypeOptional = productTypeRepository.findById(id);
        return productTypeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ProductType createProductType(@Valid @RequestBody ProductType productType) {
        return productTypeRepository.save(productType);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductType> updateProductType(@PathVariable(value = "id") Integer id,
                                                         @Valid @RequestBody ProductType updatedProductType) {
        Optional<ProductType> existingProductTypeOptional = productTypeRepository.findById(id);
        if (existingProductTypeOptional.isPresent()) {
            ProductType existingProductType = existingProductTypeOptional.get();
            existingProductType.setProductTypeName(updatedProductType.getProductTypeName());
            ProductType savedProductType = productTypeRepository.save(existingProductType);
            return ResponseEntity.ok(savedProductType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductType(@PathVariable(value = "id") Integer id) {
        Optional<ProductType> productTypeOptional = productTypeRepository.findById(id);
        if (productTypeOptional.isPresent()) {
            productTypeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
