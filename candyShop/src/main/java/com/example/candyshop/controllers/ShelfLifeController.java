package com.example.candyshop.controllers;

import com.example.candyshop.models.ShelfLife;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shelfLife")
public class ShelfLifeController {

    private final String externalApiUrl = "http://localhost:8081/api/shelf-lifes";

    private final RestTemplate restTemplate;

    public ShelfLifeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping
    public String shelfLifePage(Model model){
        ShelfLife[] shelfLifes = restTemplate.getForObject(externalApiUrl, ShelfLife[].class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String roleAutor = authentication.getAuthorities().toString();

        model.addAttribute("roleAutor", roleAutor);
        model.addAttribute("shelfLifes", shelfLifes);

        return "shelfLife";
    }


    @PostMapping("/add")
    public String addShelfLife(@ModelAttribute ShelfLife shelfLife) {
        try {
            restTemplate.postForObject(externalApiUrl + "/add", shelfLife, ShelfLife.class);
            return "redirect:/shelfLife";
        }
        catch (Exception e) {
            return "redirect:/shelfLife";
        }
    }

    @PostMapping("/update")
    public String updateShelfLife(@RequestParam Integer id, @RequestParam String shelfLifeYear) {
        try {
            ShelfLife optionalShelfLife = restTemplate.getForObject(externalApiUrl + "/" + id, ShelfLife.class);
            if (optionalShelfLife != null) {
                optionalShelfLife.setShelfLifeYear(shelfLifeYear);
                restTemplate.put(externalApiUrl + "/update/" + id, optionalShelfLife, ShelfLife.class);
            }
            return "redirect:/shelfLife";
        }
        catch (Exception e) {
            return "redirect:/shelfLife";
        }
    }

    @PostMapping("/delete")
    public String deleteShelfLife(@RequestParam Integer id) {
        try {
            restTemplate.delete(externalApiUrl + "/delete/" + id, null, Void.class);
            return "redirect:/shelfLife";
        }
        catch (Exception e) {
            return "redirect:/shelfLife";
        }
    }
}
