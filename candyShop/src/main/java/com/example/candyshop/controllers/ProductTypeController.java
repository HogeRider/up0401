package com.example.candyshop.controllers;

import com.example.candyshop.models.ProductType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/productTypes")
public class ProductTypeController {

    private final String externalApiUrl = "http://localhost:8081/api/product-types";
    private final RestTemplate restTemplate;

    public ProductTypeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String productTypeController(Model model){
        ProductType[] productTypes = restTemplate.getForObject(externalApiUrl, ProductType[].class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String roleAutor = authentication.getAuthorities().toString();

        model.addAttribute("roleAutor", roleAutor);
        model.addAttribute("productTypes",productTypes);

        return ("productTypes");
    }

    @PostMapping("/add")
    public String addProductType(@ModelAttribute ProductType productType) {
        try {

            restTemplate.postForObject(externalApiUrl + "/add", productType, ProductType.class);

            return"redirect:/productTypes";
        }
        catch (Exception e) {
            return "redirect:/productTypes";
        }
    }

    @PostMapping("/update")
    public String updateProductType(@RequestParam Integer id, @RequestParam String productTypeName) {
        try {
            ProductType optionalProductType = restTemplate.getForObject(externalApiUrl + "/" + id, ProductType.class);
            if (optionalProductType != null) {
                optionalProductType.setProductTypeName(productTypeName);
                restTemplate.put(externalApiUrl + "/update/" + id, optionalProductType, ProductType.class);
            }
            return "redirect:/productTypes";
        }
        catch (Exception e) {
            return "redirect:/productTypes";
        }
    }

    @PostMapping("/delete")
    public String deleteProductType(@RequestParam Integer id) {
        try {
            restTemplate.delete(externalApiUrl + "/delete/" + id, null, Void.class);
            return "redirect:/productTypes";
        }
        catch (Exception e) {
            return "redirect:/productTypes";
        }
    }
}
