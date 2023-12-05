package com.example.candyshop.controllers;

import com.example.candyshop.models.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/role")
public class RoleController {

    private final String roleUrl = "http://localhost:8081/api/roles";

    private final RestTemplate restTemplate;

    public RoleController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping
    public String rolePage(Model model){
        Role[] roles = restTemplate.getForObject(roleUrl, Role[].class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String roleAutor = authentication.getAuthorities().toString();

        model.addAttribute("roleAutor", roleAutor);
        model.addAttribute("roles", roles);

        return ("role");
    }

    @PostMapping("/add")
    public String addRole(@ModelAttribute Role role) {
        try {
            restTemplate.postForObject(roleUrl + "/add", role, Role.class);
            return "redirect:/role";
        }
        catch (Exception e) {
            return "redirect:/role";

        }
    }

    @PostMapping("/update")
    public String updateRole(@RequestParam Integer id, @RequestParam String name) {
        try{
            Role optionalRole = restTemplate.getForObject(roleUrl + "/" + id, Role.class);
            if (optionalRole != null) {

                optionalRole.setName(name);
                restTemplate.put(roleUrl + "/update/" + id, optionalRole, Role.class);
            }
            return "redirect:/role";
        }
        catch (Exception e) {
            return "redirect:/role";

        }

    }

    @PostMapping("/delete")
    public String deleteRole(@RequestParam Integer id) {
        try {
            restTemplate.delete(roleUrl + "/delete/" + id, null, Void.class);
            return "redirect:/role";
        }
        catch (Exception e) {
            return "redirect:/role";

        }
    }
}
