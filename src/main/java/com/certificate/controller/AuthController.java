package com.certificate.controller;

import com.certificate.dto.SignupRequest;
import com.certificate.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "signup";
    }
    
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignupRequest request, 
                        BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }
        
        try {
            userService.registerUser(request);
            return "redirect:/login?signup=success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }
}
