package com.certificate.controller;

import com.certificate.model.Certificate;
import com.certificate.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class VerificationController {
    
    private final CertificateService certificateService;
    
    @GetMapping("/verify")
    public String verifyPage(@RequestParam(required = false) String certNumber, Model model) {
        if (certNumber != null && !certNumber.isEmpty()) {
            try {
                Certificate certificate = certificateService.getCertificateByNumber(certNumber);
                model.addAttribute("certificate", certificate);
                model.addAttribute("verified", true);
            } catch (Exception e) {
                model.addAttribute("error", "Certificate not found. Please check the certificate number.");
            }
        }
        return "verify";
    }
    
    @GetMapping("/verify/{certificateNumber}")
    public String verifyCertificate(@PathVariable String certificateNumber, Model model) {
        try {
            Certificate certificate = certificateService.getCertificateByNumber(certificateNumber);
            model.addAttribute("certificate", certificate);
            model.addAttribute("verified", true);
        } catch (Exception e) {
            model.addAttribute("error", "Certificate not found or invalid.");
        }
        return "verify";
    }
}
