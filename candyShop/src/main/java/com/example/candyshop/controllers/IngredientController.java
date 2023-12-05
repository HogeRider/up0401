package com.example.candyshop.controllers;

import com.example.candyshop.models.Ingredient;
import com.example.candyshop.models.IngredientType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    private final String externalApiUrl = "http://localhost:8081/api/ingredients";
    private final String ingredientTypeUrl = "http://localhost:8081/api/ingredient-types";
    private final RestTemplate restTemplate;

    public IngredientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String ingredientPage(Model model){


        Ingredient[] ingredients = restTemplate.getForObject(externalApiUrl, Ingredient[].class);
        IngredientType[] ingredientTypes = restTemplate.getForObject(ingredientTypeUrl, IngredientType[].class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String roleAutor = authentication.getAuthorities().toString();

        model.addAttribute("roleAutor", roleAutor);
        model.addAttribute("ingredientTypes", ingredientTypes);
        model.addAttribute("ingredients", ingredients);

        return "ingredients";
    }

    @PostMapping("/add")
    public String addIngredient(
            @RequestParam String ingredientName,
            @RequestParam int ingredientArticle,
            @RequestParam Integer ingredientType_ID) {
        try {
            Ingredient ingredient = new Ingredient();
            ingredient.setIngredientName(ingredientName);
            ingredient.setIngredientArticle(ingredientArticle);
            ingredient.setIngredientType(restTemplate.getForObject(ingredientTypeUrl + "/" + ingredientType_ID, IngredientType.class));

            restTemplate.postForObject(externalApiUrl + "/add", ingredient, Ingredient.class);
            return "redirect:/ingredients";
        } catch (Exception e) {
            return "redirect:/ingredients";
        }
    }


    @PostMapping("/update")
    public String updateIngredient(@RequestParam Integer id,
                                         @RequestParam String ingredientName,
                                         @RequestParam int ingredientArticle,
                                         @RequestParam Integer ingredientType_ID) {
        try {
            Ingredient optionalIngredient = restTemplate.getForObject(externalApiUrl + "/" + id, Ingredient.class);
            if (optionalIngredient != null) {

                optionalIngredient.setIngredientName(ingredientName);
                optionalIngredient.setIngredientArticle(ingredientArticle);
                optionalIngredient.setIngredientType(restTemplate.getForObject(ingredientTypeUrl + "/" + ingredientType_ID, IngredientType.class));

                restTemplate.put(externalApiUrl + "/update/" + id, optionalIngredient, Ingredient.class);

            }
            return "redirect:/ingredients";
        } catch (Exception e) {
            return "redirect:/ingredients";
        }
    }

    @PostMapping("/delete")
    public ModelAndView deleteIngredient(@RequestParam Integer id) {
        try {
            restTemplate.delete(externalApiUrl + "/delete/" + id, null, Void.class);
            return new ModelAndView("redirect:/ingredients");
        } catch (Exception e) {
            return new ModelAndView("redirect:/ingredients");
        }
    }
}
