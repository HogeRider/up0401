package com.example.candyshop.controllers;

import com.example.candyshop.models.IngredientType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/ingredientTypes")
public class IngredientTypeController {
    private final String externalApiUrl = "http://localhost:8081/api/ingredient-types";
    private final RestTemplate restTemplate;

    public IngredientTypeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String ingredientType(Model model){
        IngredientType[] ingredientTypes = restTemplate.getForObject(externalApiUrl, IngredientType[].class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String roleAutor = authentication.getAuthorities().toString();

        model.addAttribute("roleAutor", roleAutor);
        model.addAttribute("ingredientTypes", ingredientTypes);

        return ("ingredientTypes");
    }

    @PostMapping("/add")
    public String addIngredientType(@ModelAttribute IngredientType ingredientType){
        try {

            restTemplate.postForObject(externalApiUrl + "/add", ingredientType, IngredientType.class);

            return "redirect:/ingredientTypes";
        }
        catch (Exception e){
            return "redirect:/ingredientTypes";
        }
    }

    @PostMapping("/update")
    public String updateIngredientType(@RequestParam Integer id, @RequestParam String ingredientTypeName){
        try {
            IngredientType optionalIngredientType = restTemplate.getForObject(externalApiUrl + "/" + id, IngredientType.class);
            if (optionalIngredientType != null){

                optionalIngredientType.setIngredientTypeName(ingredientTypeName);
                restTemplate.put(externalApiUrl + "/update/" + id, optionalIngredientType, IngredientType.class);
            }
            return "redirect:/ingredientTypes";
        }
        catch (Exception e){
            return "redirect:/ingredientTypes";
        }
    }

    @PostMapping("/delete")
    public String deleteIngredientType(@RequestParam Integer id){
        try {
            restTemplate.delete(externalApiUrl + "/delete/" + id, null, Void.class);

            return "redirect:/ingredientTypes";
        }
        catch (Exception e){
            return "redirect:/ingredientTypes";
        }
    }
}
