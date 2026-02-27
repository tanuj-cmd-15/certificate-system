package com.certificate.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

@Data
@Document(collection = "certificates")
public class Certificate {
    
    @Id
    private String id;
    
    private String recipientName;
    private String recipientEmail;
    private String courseName;  // Event name (e.g., NEXUX TANTRA 2K26)
    
    @Indexed(unique = true)
    private String certificateNumber;
    
    @Indexed(unique = true)
    private String credentialId;  // Unique credential ID for verification
    
    private LocalDateTime issuedDate;
    private String issuerName;
    private String issuerSignature;  // Prize (e.g., First Prize, Second Prize)
    
    // New fields for SPPU
    private String department = "Department of Technology";  // Can also be used for category
    private String university = "Savitribai Phule Pune University";
    private String verificationUrl;
    private String linkedInShareUrl;
    private boolean emailSent = false;
    private LocalDateTime emailSentDate;
    
    // Additional fields for technical events
    private String prize;  // First Prize, Second Prize, Third Prize
    private String category;  // Category of competition
    
    // Security and verification
    private String verificationHash;  // SHA-256 hash for verification
    private String qrCodeData;  // QR code data
    
    // Generate credential ID
    public void generateCredentialId() {
        try {
            String data = certificateNumber + recipientEmail + issuedDate.toString();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            this.credentialId = "CRED-" + hexString.substring(0, 16).toUpperCase();
        } catch (Exception e) {
            this.credentialId = "CRED-" + System.currentTimeMillis();
        }
    }
    
    // Generate verification hash
    public void generateVerificationHash() {
        try {
            String data = certificateNumber + recipientName + recipientEmail + issuedDate.toString();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            this.verificationHash = hexString.toString();
        } catch (Exception e) {
            this.verificationHash = "";
        }
    }
}
