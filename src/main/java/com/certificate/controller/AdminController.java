package com.certificate.controller;

import com.certificate.dto.CertificateRequest;
import com.certificate.model.Certificate;
import com.certificate.repository.UserRepository;
import com.certificate.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final CertificateService certificateService;
    private final UserRepository userRepository;
    
    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("certificates", certificateService.getAllCertificates());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("certificateRequest", new CertificateRequest());
        return "admin";
    }
    
    @PostMapping("/certificates")
    public String createCertificate(@Valid @ModelAttribute CertificateRequest request,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("certificates", certificateService.getAllCertificates());
            model.addAttribute("users", userRepository.findAll());
            return "admin";
        }
        
        Certificate certificate = new Certificate();
        certificate.setRecipientName(request.getRecipientName());
        certificate.setRecipientEmail(request.getRecipientEmail());
        certificate.setCourseName(request.getCourseName());
        certificate.setIssuerName(request.getIssuerName());
        certificate.setPrize(request.getPrize());
        certificate.setCategory(request.getCategory());
        certificate.setIssuerSignature(request.getPrize());  // Store prize in signature field for display
        
        certificateService.generateCertificate(certificate);
        return "redirect:/admin?success";
    }
    
    @GetMapping("/certificates/{id}/delete")
    public String deleteCertificate(@PathVariable String id) {
        certificateService.deleteCertificate(id);
        return "redirect:/admin?deleted";
    }
}
