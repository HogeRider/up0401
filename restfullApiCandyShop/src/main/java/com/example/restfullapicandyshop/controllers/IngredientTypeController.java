package com.example.restfullapicandyshop.controllers;

import com.example.restfullapicandyshop.models.IngredientType;
import com.example.restfullapicandyshop.repositories.IngredientTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingredient-types")
public class IngredientTypeController {

    @Autowired
    private IngredientTypeRepository ingredientTypeRepository;

    @GetMapping
    public List<IngredientType> getAllIngredientTypes() {
        return ingredientTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientType> getIngredientTypeById(@PathVariable(value = "id") Integer id) {
        Optional<IngredientType> ingredientTypeOptional = ingredientTypeRepository.findById(id);
        return ingredientTypeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public IngredientType createIngredientType(@Valid @RequestBody IngredientType ingredientType) {
        return ingredientTypeRepository.save(ingredientType);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<IngredientType> updateIngredientType(@PathVariable(value = "id") Integer id,
                                                               @Valid @RequestBody IngredientType updatedIngredientType) {
        Optional<IngredientType> existingIngredientTypeOptional = ingredientTypeRepository.findById(id);
        if (existingIngredientTypeOptional.isPresent()) {
            IngredientType existingIngredientType = existingIngredientTypeOptional.get();
            existingIngredientType.setIngredientTypeName(updatedIngredientType.getIngredientTypeName());
            IngredientType savedIngredientType = ingredientTypeRepository.save(existingIngredientType);
            return ResponseEntity.ok(savedIngredientType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIngredientType(@PathVariable(value = "id") Integer id) {
        Optional<IngredientType> ingredientTypeOptional = ingredientTypeRepository.findById(id);
        if (ingredientTypeOptional.isPresent()) {
            ingredientTypeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
