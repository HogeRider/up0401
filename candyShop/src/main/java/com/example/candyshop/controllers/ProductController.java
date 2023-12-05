package com.example.candyshop.controllers;

import com.example.candyshop.models.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final String externalApiUrl = "http://localhost:8081/api/products";
    private final String shelfLifeUrl = "http://localhost:8081/api/shelf-lifes";
    private final String ingredientUrl = "http://localhost:8081/api/ingredients";
    private final String productTypeUrl = "http://localhost:8081/api/product-types";

    private final RestTemplate restTemplate;

    public ProductController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String productPage(Model model){

        ProductType[] productTypes = restTemplate.getForObject(productTypeUrl, ProductType[].class);
        ShelfLife[] shelfLifes = restTemplate.getForObject(shelfLifeUrl, ShelfLife[].class);
        Ingredient[] ingredients = restTemplate.getForObject(ingredientUrl, Ingredient[].class);
        Product[] products = restTemplate.getForObject(externalApiUrl, Product[].class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String roleAutor = authentication.getAuthorities().toString();

        model.addAttribute("roleAutor", roleAutor);
        model.addAttribute("shelfLifes", shelfLifes);
        model.addAttribute("productTypes",productTypes);
        model.addAttribute("ingredients",ingredients);
        model.addAttribute("products",products);



        return "products";
    }

    @PostMapping("/add")
    public String  addProduct(@RequestParam int productType_Id,
                                   @RequestParam int shelfLife_Id,
                                   @RequestParam int ingredient_Id,
                                   @RequestParam BigDecimal productCost,
                                   @RequestParam Integer productCount,
                                   @RequestParam String productName){

        try{
            Product product = new Product();
            product.setProductType(restTemplate.getForObject(productTypeUrl+"/" + productType_Id, ProductType.class));
            product.setShelfLife(restTemplate.getForObject(shelfLifeUrl+"/" + shelfLife_Id, ShelfLife.class));
            product.setIngredient(restTemplate.getForObject(ingredientUrl+"/" + ingredient_Id, Ingredient.class));
            product.setProductCost(productCost);
            product.setProductCount(productCount);
            product.setProductName(productName);

            restTemplate.postForObject(externalApiUrl + "/add", product, Product.class);

            return ("redirect:/products");
        }
        catch (Exception e){

            return ("redirect:/products");
        }
    }

    @PostMapping("/update")
    public String updateProduct(@RequestParam Integer id,
                                      @RequestParam int productType_Id,
                                   @RequestParam int shelfLife_Id,
                                   @RequestParam int ingredient_Id,
                                   @RequestParam BigDecimal productCost,
                                   @RequestParam Integer productCount,
                                   @RequestParam String productName){

        try{
            Product optionalProduct = restTemplate.getForObject(externalApiUrl + "/" + id, Product.class);
            if (optionalProduct != null){
                Product product = optionalProduct;

                product.setProductType(restTemplate.getForObject(productTypeUrl+"/" + productType_Id, ProductType.class));
                product.setShelfLife(restTemplate.getForObject(shelfLifeUrl+"/" + shelfLife_Id, ShelfLife.class));
                product.setIngredient(restTemplate.getForObject(ingredientUrl+"/" + ingredient_Id, Ingredient.class));
                product.setProductCost(productCost);
                product.setProductCount(productCount);
                product.setProductName(productName);

                restTemplate.put(externalApiUrl + "/update/" + id, product, Product.class);
            }
            return ("redirect:/products");
        }
        catch (Exception e){

            return ("redirect:/products");
        }
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_Продавец')")
    public String deleteProduct (@RequestParam Integer id){
        try{
            restTemplate.delete(externalApiUrl + "/delete/" + id, null, Void.class);
            return ("redirect:/products");
        }
        catch (Exception e){

            return ("redirect:/products");
        }
    }

}
