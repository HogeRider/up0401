package com.example.candyshop.controllers;

import com.example.candyshop.models.History;
import com.example.candyshop.models.Product;
import com.example.candyshop.models.Treaty;
import com.example.candyshop.models.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/treaties")
public class TreatyController {
    private final String treatyUrl = "http://localhost:8081/api/treaties";
    private final String userUrl = "http://localhost:8081/api/users";
    private final RestTemplate restTemplate;

    public TreatyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String treatysPage(Model model){
        User[] users = restTemplate.getForObject(userUrl, User[].class);
        Treaty[] treaties = restTemplate.getForObject(treatyUrl, Treaty[].class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String roleAutor = authentication.getAuthorities().toString();

        model.addAttribute("roleAutor", roleAutor);
        model.addAttribute("treaties", treaties);
        model.addAttribute("users", users);

        return ("treaties");
    }

    @PostMapping("/add")
    public String addTreaty(@RequestParam String treatyNumber,
                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfConclusion,
                                  @RequestParam BigDecimal amount,
                                  @RequestParam String treatyContent,
                                  @RequestParam Integer user_Id)
    {
        try{
            Treaty treaty = new Treaty();
            treaty.setTreatyNumber(treatyNumber);
            treaty.setTreatyContent(treatyContent);
            treaty.setDateOfConclusion(dateOfConclusion);
            treaty.setAmount(amount);
            treaty.setUser(restTemplate.getForObject(userUrl+"/" + user_Id, User.class));

            restTemplate.postForObject(treatyUrl + "/add", treaty, Treaty.class);

            return "redirect:/treaties";
        }
        catch (Exception e){

            return "redirect:/treaties";
        }
    }

    @PostMapping("/update")
    public String updateTreaty(@RequestParam Integer id,
                                     @RequestParam String treatyNumber,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfConclusion,
                                     @RequestParam BigDecimal amount,
                                     @RequestParam String treatyContent,
                                     @RequestParam Integer user_Id)
    {
        try{
            Treaty existingTreaty = restTemplate.getForObject(userUrl + "/" + id, Treaty.class);
            if (existingTreaty != null) {
                existingTreaty.setTreatyNumber(treatyNumber);
                existingTreaty.setTreatyContent(treatyContent);
                existingTreaty.setDateOfConclusion(dateOfConclusion);
                existingTreaty.setAmount(amount);

                existingTreaty.setUser(restTemplate.getForObject(userUrl+"/" + user_Id, User.class));

                restTemplate.put(treatyUrl + "/update/" + id, existingTreaty, Treaty.class);
            }
            return "redirect:/treaties";
        }
        catch (Exception e){

            return "redirect:/treaties";
        }
    }

    @PostMapping("/delete")
    public String deleteTreaty (@RequestParam Integer id){
        try{
            restTemplate.delete(treatyUrl + "/delete/" + id, null, Void.class);
            return "redirect:/treaties";
        }
        catch (Exception e){
            return "redirect:/treaties";
        }
    }
}
