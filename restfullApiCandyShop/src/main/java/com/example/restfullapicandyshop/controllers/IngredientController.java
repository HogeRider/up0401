package com.example.restfullapicandyshop.controllers;

import com.example.restfullapicandyshop.models.Ingredient;
import com.example.restfullapicandyshop.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable(value = "id") Integer id) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);
        return ingredientOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public Ingredient createIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable(value = "id") Integer id,
                                                       @Valid @RequestBody Ingredient updatedIngredient) {
        Optional<Ingredient> existingIngredientOptional = ingredientRepository.findById(id);
        if (existingIngredientOptional.isPresent()) {
            Ingredient existingIngredient = existingIngredientOptional.get();
            existingIngredient.setIngredientArticle(updatedIngredient.getIngredientArticle());
            existingIngredient.setIngredientName(updatedIngredient.getIngredientName());
            existingIngredient.setIngredientType(updatedIngredient.getIngredientType());
            Ingredient savedIngredient = ingredientRepository.save(existingIngredient);
            return ResponseEntity.ok(savedIngredient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable(value = "id") Integer id) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);
        if (ingredientOptional.isPresent()) {
            ingredientRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
