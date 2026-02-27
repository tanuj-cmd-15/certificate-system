# Certificate System - Testing Guide

## Application is Running!
- URL: http://localhost:8080
- MongoDB: Connected to localhost:27017
- Public Verification: http://localhost:8080/verify

## Pre-created Admin Account
- **Username:** admin
- **Password:** admin123

## New Features Added ✨

### 1. Unique Certificate Numbers
- Format: `SPPU-DOT-YYYYMMDDHHMMSS-XXXX`
- Example: `SPPU-DOT-20260227143025-7834`
- Automatically generated for each certificate

### 2. Email Notifications
- Certificates are automatically emailed to recipients
- Email includes certificate details and verification link
- LinkedIn share button included
- See EMAIL_SETUP.md for configuration

### 3. Public Certificate Verification
- Anyone can verify certificates at: http://localhost:8080/verify
- No login required
- Enter certificate number to verify authenticity
- Shows official SPPU branding

### 4. LinkedIn Integration
- Direct "Share on LinkedIn" button
- Pre-filled with certificate details
- Links to verification page
- Professional credential sharing

### 5. SPPU Branding
- University: Savitribai Phule Pune University
- Department: Department of Technology
- Official certificate format
- Professional design

## Testing Flow

### 1. Sign Up (New User Registration)
1. Open browser: http://localhost:8080
2. Click "Sign up here"
3. Fill in the signup form:
   - Username: testuser
   - Email: test@example.com
   - Full Name: Test User
   - Password: test123
4. Click "Sign Up"

### 2. Login as Admin
1. Go to: http://localhost:8080/login
2. Enter credentials:
   - Username: admin
   - Password: admin123
3. Click "Login"

### 3. Generate Certificate (Admin)
1. Click "Admin Panel" in navbar
2. Fill the certificate form:
   - Recipient Name: John Doe
   - Recipient Email: john@example.com
   - Course Name: Advanced Web Development
   - Issuer Name: (optional - defaults to SPPU DOT)
3. Click "Generate Certificate"
4. Certificate is created with unique number
5. Email is sent to recipient (if configured)
6. View certificate in the table

### 4. Verify Certificate (Public Access)
1. Open new browser tab (no login needed)
2. Go to: http://localhost:8080/verify
3. Enter certificate number from admin panel
4. Click "Verify"
5. See official certificate with:
   - SPPU branding
   - Verification badge
   - All certificate details
   - LinkedIn share button
   - Print option

### 5. Share on LinkedIn
1. From verification page, click "Share on LinkedIn"
2. LinkedIn opens with pre-filled data:
   - Certification name
   - Organization (SPPU)
   - Issue date
   - Certificate URL
   - Certificate ID
3. Add to your LinkedIn profile

### 6. View Certificate Details
In Admin Panel, certificates table shows:
- Certificate Number (clickable to verify)
- Recipient name and email
- Course name
- Email status (Sent/Failed)
- Issue date
- Delete option

### 7. Search & Verify
Public verification page allows:
- Search by certificate number
- Instant verification
- Print certificate
- Share on social media
- No authentication required

## API Endpoints

### Public Endpoints (No Auth Required)
```bash
# Verify certificate by number
GET http://localhost:8080/verify/SPPU-DOT-20260227143025-7834

# Verification page with search
GET http://localhost:8080/verify
```

### Protected Endpoints (Requires Auth)
```bash
# Get all certificates
GET http://localhost:8080/api/certificates

# Create certificate
POST http://localhost:8080/api/certificates
Content-Type: application/json
{
  "recipientName": "Jane Smith",
  "recipientEmail": "jane@example.com",
  "courseName": "MongoDB Basics",
  "issuerName": "SPPU DOT"
}
```

## Features Implemented

✅ User Registration (Signup)
✅ User Login with Spring Security
✅ Role-based Access Control (USER, ADMIN)
✅ Admin Dashboard
✅ Certificate Generation with Unique Numbers
✅ Email Notifications to Recipients
✅ Public Certificate Verification (No Login)
✅ LinkedIn Integration & Sharing
✅ Certificate Management (Create, View, Delete)
✅ User Management (View all users)
✅ Responsive UI with SPPU Branding
✅ Form Validation
✅ MongoDB Integration
✅ Password Encryption (BCrypt)
✅ Searchable Certificates
✅ Printable Certificates
✅ Email Status Tracking

## Certificate Number Format

**SPPU-DOT-YYYYMMDDHHMMSS-XXXX**
- `SPPU-DOT`: Prefix for Savitribai Phule Pune University - Department of Technology
- `YYYYMMDDHHMMSS`: Timestamp (Year, Month, Day, Hour, Minute, Second)
- `XXXX`: Random 4-digit number

Example: `SPPU-DOT-20260227143025-7834`

## Email Configuration

To enable email sending:
1. See EMAIL_SETUP.md for detailed instructions
2. Configure Gmail App Password or other SMTP
3. Update application.yaml or set environment variables
4. Restart application

Without email configuration:
- Certificates still generate
- Email status shows "Failed"
- All other features work normally

## Technology Stack
- Spring Boot 4.0.3
- Spring Security
- Spring Data MongoDB
- Spring Mail
- Thymeleaf (Template Engine)
- MongoDB
- Lombok
- Bean Validation

## Production Deployment Notes

For production:
1. Set `app.base-url` to your domain (e.g., https://certificates.sppu.edu.in)
2. Configure production email service
3. Enable HTTPS
4. Set up proper MongoDB authentication
5. Configure firewall rules
6. Set up backup strategy
7. Monitor email delivery
8. Add rate limiting for verification endpoint

## Support

For issues or questions:
- Check MongoDB is running: `tasklist | findstr mongod`
- Check application logs in terminal
- Verify email configuration in application.yaml
- Test verification page without login
