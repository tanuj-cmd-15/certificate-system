package com.certificate.service;

import com.certificate.model.Certificate;
import com.certificate.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {
    
    private final CertificateRepository certificateRepository;
    private final EmailService emailService;
    private final QRCodeService qrCodeService;
    
    @Value("${app.base-url}")
    private String baseUrl;
    
    @Value("${app.certificate.prefix}")
    private String certificatePrefix;
    
    @Value("${app.certificate.issuer}")
    private String defaultIssuer;
    
    public Certificate generateCertificate(Certificate certificate) {
        // Generate unique certificate number
        String certNumber = generateCertificateNumber();
        certificate.setCertificateNumber(certNumber);
        certificate.setIssuedDate(LocalDateTime.now());
        
        // Generate credential ID and verification hash
        certificate.generateCredentialId();
        certificate.generateVerificationHash();
        
        // Set default issuer if not provided
        if (certificate.getIssuerName() == null || certificate.getIssuerName().isEmpty()) {
            certificate.setIssuerName(defaultIssuer);
        }
        
        // Generate verification URL
        String verificationUrl = baseUrl + "/verify/" + certNumber;
        certificate.setVerificationUrl(verificationUrl);
        
        // Generate QR code for verification
        String qrData = verificationUrl + "?cred=" + certificate.getCredentialId();
        String qrCodeBase64 = qrCodeService.generateQRCodeBase64(qrData, 200, 200);
        certificate.setQrCodeData(qrCodeBase64);
        
        // Generate LinkedIn share URL
        String linkedInUrl = generateLinkedInShareUrl(certificate, verificationUrl);
        certificate.setLinkedInShareUrl(linkedInUrl);
        
        // Save certificate
        Certificate savedCertificate = certificateRepository.save(certificate);
        
        // Send email to recipient
        try {
            emailService.sendCertificateEmail(savedCertificate);
            savedCertificate.setEmailSent(true);
            savedCertificate.setEmailSentDate(LocalDateTime.now());
            certificateRepository.save(savedCertificate);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
        
        return savedCertificate;
    }
    
    private String generateCertificateNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return certificatePrefix + "-" + timestamp + "-" + random;
    }
    
    private String generateLinkedInShareUrl(Certificate certificate, String verificationUrl) {
        try {
            String name = URLEncoder.encode(certificate.getCourseName(), StandardCharsets.UTF_8);
            String organization = URLEncoder.encode(certificate.getUniversity(), StandardCharsets.UTF_8);
            String certUrl = URLEncoder.encode(verificationUrl, StandardCharsets.UTF_8);
            String certId = URLEncoder.encode(certificate.getCertificateNumber(), StandardCharsets.UTF_8);
            
            return String.format(
                "https://www.linkedin.com/profile/add?startTask=CERTIFICATION_NAME&name=%s&organizationName=%s&issueYear=%d&issueMonth=%d&certUrl=%s&certId=%s",
                name,
                organization,
                certificate.getIssuedDate().getYear(),
                certificate.getIssuedDate().getMonthValue(),
                certUrl,
                certId
            );
        } catch (Exception e) {
            return "https://www.linkedin.com/";
        }
    }
    
    public Certificate getCertificateByNumber(String certificateNumber) {
        return certificateRepository.findByCertificateNumber(certificateNumber)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
    }
    
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }
    
    public void deleteCertificate(String id) {
        certificateRepository.deleteById(id);
    }
    
    public List<Certificate> searchCertificates(String query) {
        // Search by certificate number, recipient name, or email
        return certificateRepository.findAll().stream()
                .filter(cert -> 
                    cert.getCertificateNumber().toLowerCase().contains(query.toLowerCase()) ||
                    cert.getRecipientName().toLowerCase().contains(query.toLowerCase()) ||
                    cert.getRecipientEmail().toLowerCase().contains(query.toLowerCase()) ||
                    (cert.getCredentialId() != null && cert.getCredentialId().toLowerCase().contains(query.toLowerCase()))
                )
                .toList();
    }
    
    public boolean verifyCredential(String certificateNumber, String credentialId) {
        try {
            Certificate cert = getCertificateByNumber(certificateNumber);
            return cert.getCredentialId() != null && cert.getCredentialId().equals(credentialId);
        } catch (Exception e) {
            return false;
        }
    }
}
