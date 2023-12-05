package com.example.restfullapicandyshop.controllers;

import com.example.restfullapicandyshop.models.ShelfLife;
import com.example.restfullapicandyshop.repositories.ShelfLifeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shelf-lifes")
public class ShelfLifeController {

    @Autowired
    private ShelfLifeRepository shelfLifeRepository;

    @GetMapping
    public List<ShelfLife> getAllShelfLifes() {
        return shelfLifeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShelfLife> getShelfLifeById(@PathVariable(value = "id") Integer id) {
        Optional<ShelfLife> shelfLifeOptional = shelfLifeRepository.findById(id);
        return shelfLifeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ShelfLife createShelfLife(@Valid @RequestBody ShelfLife shelfLife) {
        return shelfLifeRepository.save(shelfLife);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShelfLife> updateShelfLife(@PathVariable(value = "id") Integer id,
                                                     @Valid @RequestBody ShelfLife updatedShelfLife) {
        Optional<ShelfLife> existingShelfLifeOptional = shelfLifeRepository.findById(id);
        if (existingShelfLifeOptional.isPresent()) {
            ShelfLife existingShelfLife = existingShelfLifeOptional.get();
            existingShelfLife.setShelfLifeYear(updatedShelfLife.getShelfLifeYear());
            ShelfLife savedShelfLife = shelfLifeRepository.save(existingShelfLife);
            return ResponseEntity.ok(savedShelfLife);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShelfLife(@PathVariable(value = "id") Integer id) {
        Optional<ShelfLife> shelfLifeOptional = shelfLifeRepository.findById(id);
        if (shelfLifeOptional.isPresent()) {
            shelfLifeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
