package com.certificate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CertificateRequest {
    
    @NotBlank(message = "Recipient name is required")
    private String recipientName;
    
    @NotBlank(message = "Recipient email is required")
    @Email(message = "Email should be valid")
    private String recipientEmail;
    
    @NotBlank(message = "Event/Course name is required")
    private String courseName;  // Event name (e.g., NEXUX TANTRA 2K26)
    
    private String issuerName;
    
    private String prize;  // First Prize, Second Prize, Third Prize, etc.
    
    private String category;  // Competition category
}
