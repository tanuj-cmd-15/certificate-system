package com.certificate.service;

import com.certificate.model.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    @Value("${app.name}")
    private String appName;
    
    @Value("${app.base-url}")
    private String baseUrl;
    
    @Value("${spring.mail.username:}")
    private String mailUsername;
    
    @Value("${spring.mail.enabled:false}")
    private boolean mailEnabled;
    
    public void sendCertificateEmail(Certificate certificate) {
        // Check if email is configured
        if (!mailEnabled || mailUsername == null || mailUsername.isEmpty()) {
            System.out.println("⚠️  Email not configured. Skipping email for: " + certificate.getRecipientEmail());
            System.out.println("📝 To enable email, see EMAIL_SETUP.md");
            throw new RuntimeException("Email not configured");
        }
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(mailUsername);
            helper.setTo(certificate.getRecipientEmail());
            helper.setSubject("🎓 Your Certificate from " + appName);
            
            String emailContent = buildEmailContent(certificate);
            helper.setText(emailContent, true);
            
            mailSender.send(message);
            System.out.println("✅ Email sent successfully to: " + certificate.getRecipientEmail());
        } catch (MessagingException e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
    
    private String buildEmailContent(Certificate certificate) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .certificate-box { background: white; padding: 20px; border-left: 4px solid #667eea; margin: 20px 0; }
                    .button { display: inline-block; padding: 12px 30px; background: #667eea; color: white; text-decoration: none; border-radius: 5px; margin: 10px 5px; }
                    .footer { text-align: center; margin-top: 20px; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🎓 Congratulations!</h1>
                        <p>You've earned a certificate</p>
                    </div>
                    <div class="content">
                        <p>Dear <strong>%s</strong>,</p>
                        
                        <p>Congratulations on successfully completing <strong>%s</strong>!</p>
                        
                        <div class="certificate-box">
                            <h3>Certificate Details</h3>
                            <p><strong>Certificate Number:</strong> %s</p>
                            <p><strong>Course:</strong> %s</p>
                            <p><strong>Issued By:</strong> %s</p>
                            <p><strong>University:</strong> %s</p>
                            <p><strong>Issue Date:</strong> %s</p>
                        </div>
                        
                        <p><strong>What you can do:</strong></p>
                        <ul>
                            <li>View and download your certificate</li>
                            <li>Share on LinkedIn</li>
                            <li>Verify authenticity anytime</li>
                        </ul>
                        
                        <div style="text-align: center; margin: 30px 0;">
                            <a href="%s/verify/%s" class="button">View Certificate</a>
                            <a href="%s" class="button" style="background: #0077b5;">Share on LinkedIn</a>
                        </div>
                        
                        <p>Anyone can verify your certificate using the certificate number at:<br>
                        <a href="%s/verify">%s/verify</a></p>
                        
                        <div class="footer">
                            <p>This is an official certificate from %s</p>
                            <p>© 2026 Savitribai Phule Pune University - Department of Technology</p>
                        </div>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                certificate.getRecipientName(),
                certificate.getCourseName(),
                certificate.getCertificateNumber(),
                certificate.getCourseName(),
                certificate.getIssuerName(),
                certificate.getUniversity(),
                certificate.getIssuedDate().toLocalDate(),
                baseUrl,
                certificate.getCertificateNumber(),
                certificate.getLinkedInShareUrl(),
                baseUrl,
                baseUrl,
                appName
            );
    }
}
