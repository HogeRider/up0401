package com.example.candyshop.controllers;

import com.example.candyshop.models.History;
import com.example.candyshop.models.Product;
import com.example.candyshop.models.Treaty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/histories")
public class HistoryController {
    private final String externalApiUrl = "http://localhost:8081/api/history";
    private final String treatyUrl = "http://localhost:8081/api/treaties";
    private final String productUrl = "http://localhost:8081/api/products";
    private final RestTemplate restTemplate;

    public HistoryController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String historyPage(Model model){
        History[] histories = restTemplate.getForObject(externalApiUrl, History[].class);
        Treaty[] treaties = restTemplate.getForObject(treatyUrl, Treaty[].class);
        Product[] products = restTemplate.getForObject(productUrl, Product[].class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String roleAutor = authentication.getAuthorities().toString();

        model.addAttribute("roleAutor", roleAutor);
        model.addAttribute("histories", histories);
        model.addAttribute("treaties", treaties);
        model.addAttribute("products", products);

        return ("histories");
    }



    @PostMapping("/add")
    public String addHistory(@RequestParam Integer treaty_Id,
                                   @RequestParam Integer product_Id,
                                   @RequestParam Integer productCountHistory) {
        try {
            History history = new History();
            history.setProductCountHistory(productCountHistory);
            history.setTreaty(restTemplate.getForObject(treatyUrl+"/" + treaty_Id, Treaty.class));
            history.setProduct(restTemplate.getForObject(productUrl+"/" + product_Id, Product.class));

            restTemplate.postForObject(externalApiUrl + "/add", history, History.class);

            return "redirect:/histories";
        } catch (Exception e) {
            return "redirect:/histories";
        }
    }

    @PostMapping("/update")
    public String updateHistory(@RequestParam Integer id,
                                      @RequestParam Integer treaty_Id,
                                      @RequestParam Integer product_Id,
                                      @RequestParam Integer productCountHistory) {
        try {
            History existingHistory = restTemplate.getForObject(externalApiUrl + "/" + id, History.class);

            if (existingHistory != null) {
                existingHistory.setProductCountHistory(productCountHistory);
                existingHistory.setTreaty(restTemplate.getForObject(treatyUrl+"/" + treaty_Id, Treaty.class));
                existingHistory.setProduct(restTemplate.getForObject(productUrl+"/" + product_Id, Product.class));

                restTemplate.put(externalApiUrl + "/update/" + id, existingHistory, History.class);
            }

            return "redirect:/histories";
        } catch (Exception e) {
            return "redirect:/histories";
        }
    }

    @PostMapping("/delete")
    public String deleteHistory(@RequestParam Integer id) {
        try {
            restTemplate.delete(externalApiUrl + "/delete/" + id, null, Void.class);

            return "redirect:/histories";
        } catch (Exception e) {
            return "redirect:/histories";
        }
    }
}
