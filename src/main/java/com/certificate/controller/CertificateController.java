package com.certificate.controller;

import com.certificate.model.Certificate;
import com.certificate.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {
    
    private final CertificateService certificateService;
    
    @PostMapping
    public ResponseEntity<Certificate> createCertificate(@RequestBody Certificate certificate) {
        return ResponseEntity.ok(certificateService.generateCertificate(certificate));
    }
    
    @GetMapping("/{certificateNumber}")
    public ResponseEntity<Certificate> getCertificate(@PathVariable String certificateNumber) {
        return ResponseEntity.ok(certificateService.getCertificateByNumber(certificateNumber));
    }
    
    @GetMapping
    public ResponseEntity<List<Certificate>> getAllCertificates() {
        return ResponseEntity.ok(certificateService.getAllCertificates());
    }
}
