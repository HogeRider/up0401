package com.example.candyshop.controllers;

import com.example.candyshop.models.Role;
import com.example.candyshop.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class RegistrationController {
    private final String externalApiUrl = "http://localhost:8081/api/users";
    private final String roleUrl = "http://localhost:8081/api/roles";

    private final RestTemplate restTemplate;

    private PasswordEncoder passwordEncoder;


    public RegistrationController(RestTemplate restTemplate, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }




    @GetMapping("/register")
    public String registrationPage(Model model){
        Role[] roles = restTemplate.getForObject(roleUrl, Role[].class);

        model.addAttribute("roles", roles);

        return ("register");
    }


    @PostMapping("/register")
    public ModelAndView register(User user) {
        try {

            Role role = restTemplate.getForObject(roleUrl + "/name/" + user.getRoleName(), Role.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEncryptKey("noKey");
            user.setRole(role);
            restTemplate.postForObject(externalApiUrl + "/add", user, User.class);
            return new ModelAndView("redirect:/login");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/register");
        }
    }

    @PostMapping("/register-form")
    public Object registerFromForm(@RequestParam Map<String, String> formData) {
        try {
            User user = new User();
            user.setFirstName(formData.get("firstName"));
            user.setSecondName(formData.get("lastName"));
            user.setMiddleName(formData.get("middleName"));
            user.setGender(formData.get("gender"));
            user.setLogin(formData.get("login"));
            user.setPassword(formData.get("password"));
            user.setPhoneNumber(formData.get("phoneNumber"));
            user.setEmail(formData.get("email"));

            user.setRoleName(formData.get("role"));

            return register(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during registration";
        }
    }
}
