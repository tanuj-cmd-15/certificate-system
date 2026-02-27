# SPPU Department of Technology - Certificate System

## 🎓 Digital Certificate Management Platform

A complete certificate generation, verification, and management system for **Savitribai Phule Pune University - Department of Technology**.

## ✨ Key Features

- ✅ **Unique Certificate Numbers** - Format: `SPPU-DOT-YYYYMMDDHHMMSS-XXXX`
- ✅ **🆕 Unique Credential IDs** - SHA-256 based credential verification
- ✅ **🆕 QR Code Verification** - Scan to instantly verify certificates
- ✅ **🆕 Verification Hash** - Cryptographic integrity checking
- ✅ **Public Verification** - Anyone can verify certificates without login
- ✅ **LinkedIn Integration** - One-click sharing to LinkedIn profiles
- ✅ **Email Notifications** - Automatic email to certificate recipients (optional)
- ✅ **Admin Dashboard** - Complete certificate and user management
- ✅ **SPPU Branding** - Official university design and credentials
- ✅ **Searchable** - Find certificates by number, name, or email
- ✅ **Print-Friendly** - Professional certificate printing
- ✅ **Secure** - Role-based access control with Spring Security

## 🚀 Production Deployment

### ❌ Vercel Not Supported
Vercel does NOT support Spring Boot applications. Use these instead:

### ✅ Recommended Platforms

1. **Railway** (Easiest - 5 minutes)
   - Built-in MongoDB
   - $5 free credit/month
   - No cold starts
   - 📖 [DEPLOY_RAILWAY.md](DEPLOY_RAILWAY.md)

2. **Render** (Best free tier)
   - 750 hours/month free
   - External MongoDB (Atlas)
   - Professional features
   - 📖 [DEPLOY_RENDER.md](DEPLOY_RENDER.md)

3. **AWS/Azure/Google Cloud** (Enterprise)
   - Maximum scalability
   - Advanced features
   - Requires DevOps knowledge

📖 **Full comparison**: [DEPLOYMENT_OPTIONS.md](DEPLOYMENT_OPTIONS.md)

---

## 🚀 Quick Start

### Prerequisites
- Java 25
- MongoDB (running on localhost:27017)
- Maven (included via wrapper)

### Start Application
```bash
./mvnw spring-boot:run -DskipTests
```

### Access
- **Application:** http://localhost:8080
- **Admin Login:** Username: `admin`, Password: `admin123`
- **Public Verification:** http://localhost:8080/verify

## 📧 Email Configuration (Optional)

Email is **optional**. The application works perfectly without it.

### To Enable Email:

**Option 1: Run Setup Script**
```bash
setup-email.bat
```

**Option 2: Manual Configuration**
1. Enable 2FA on Gmail: https://myaccount.google.com/security
2. Generate App Password: https://myaccount.google.com/apppasswords
3. Set environment variables:
```bash
setx MAIL_USERNAME "your-email@gmail.com"
setx MAIL_PASSWORD "your-16-char-app-password"
setx MAIL_ENABLED "true"
```
4. Restart application

### Without Email
- Certificates are generated normally
- Email status shows "Failed"
- All other features work perfectly
- Console shows: `⚠️ Email not configured. Skipping email...`

## 📖 Documentation

- **[QUICK_START.md](QUICK_START.md)** - Get started in 3 steps
- **[DEPLOYMENT_OPTIONS.md](DEPLOYMENT_OPTIONS.md)** - 🆕 Deploy to production (Railway, Render, AWS)
- **[DEPLOY_RAILWAY.md](DEPLOY_RAILWAY.md)** - 🆕 Deploy to Railway (easiest)
- **[DEPLOY_RENDER.md](DEPLOY_RENDER.md)** - 🆕 Deploy to Render (free tier)
- **[TESTING_GUIDE.md](TESTING_GUIDE.md)** - Complete testing instructions
- **[TEST_CREDENTIALS.md](TEST_CREDENTIALS.md)** - Test credential verification system
- **[CREDENTIAL_VERIFICATION.md](CREDENTIAL_VERIFICATION.md)** - Credential & QR code guide
- **[EMAIL_SETUP.md](EMAIL_SETUP.md)** - Detailed email configuration
- **[FEATURES_SUMMARY.md](FEATURES_SUMMARY.md)** - All features explained
- **[CERTIFICATE_DESIGN.md](CERTIFICATE_DESIGN.md)** - Certificate design specifications

## 🎯 Usage Flow

### 1. Admin Generates Certificate
1. Login as admin
2. Go to Admin Panel
3. Fill certificate form
4. Click "Generate Certificate"
5. Unique number generated (e.g., `SPPU-DOT-20260227143025-7834`)
6. Email sent to recipient (if configured)

### 2. Recipient Receives Certificate
- Receives email with certificate details
- Clicks verification link
- Views official SPPU certificate
- Can share on LinkedIn
- Can print certificate

### 3. Public Verification
- Anyone visits http://localhost:8080/verify
- Enters certificate number
- Sees official certificate with verification badge
- No login required

### 4. LinkedIn Sharing
- From verification page
- Click "Share on LinkedIn"
- Pre-filled with certificate details
- Adds to LinkedIn profile

## 🔧 Technology Stack

- **Backend:** Spring Boot 4.0.3
- **Security:** Spring Security with BCrypt
- **Database:** MongoDB
- **Email:** Spring Mail (optional)
- **Template Engine:** Thymeleaf
- **QR Code:** Google ZXing 3.5.2 🆕
- **Cryptography:** SHA-256 hashing 🆕
- **Build Tool:** Maven
- **Java Version:** 25

## 📊 Database Collections

### Certificates
```javascript
{
  certificateNumber: "SPPU-DOT-20260227143025-7834",
  credentialId: "CRED-A1B2C3D4E5F6G7H8",  // 🆕 Unique credential ID
  verificationHash: "sha256_hash...",      // 🆕 Integrity hash
  qrCodeData: "data:image/png;base64,...", // 🆕 QR code image
  recipientName: "John Doe",
  recipientEmail: "john@example.com",
  courseName: "NEXUX TANTRA 2K26",
  prize: "First Prize",                    // 🆕 Prize/position
  category: "Web Development",             // 🆕 Competition category
  issuedDate: ISODate("2026-02-27T10:45:30Z"),
  issuerName: "SPPU DOT",
  university: "Savitribai Phule Pune University",
  department: "Department of Technology",
  verificationUrl: "http://localhost:8080/verify/SPPU-DOT-...",
  linkedInShareUrl: "https://www.linkedin.com/profile/add?...",
  emailSent: true,
  emailSentDate: ISODate("2026-02-27T10:45:31Z")
}
```

### Users
```javascript
{
  username: "admin",
  email: "admin@certificate.com",
  password: "$2a$10$...", // BCrypt encrypted
  fullName: "System Administrator",
  roles: ["ROLE_ADMIN", "ROLE_USER"],
  enabled: true,
  createdAt: ISODate("2026-02-27T10:00:00Z")
}
```

## 🔐 Security

- **Authentication:** Spring Security with form login
- **Password Encryption:** BCrypt
- **Role-Based Access:**
  - `ROLE_USER` - View certificates
  - `ROLE_ADMIN` - Full access (create, delete, manage)
- **Public Endpoints:** `/verify`, `/signup`, `/login`
- **Protected Endpoints:** `/admin`, `/dashboard`, `/api/certificates`

## 🌐 API Endpoints

### Public (No Authentication)
```
GET  /verify                    - Verification page
GET  /verify/{certificateNumber} - View specific certificate
GET  /login                     - Login page
POST /signup                    - User registration
```

### Protected (Authentication Required)
```
GET  /dashboard                 - User dashboard
GET  /admin                     - Admin panel (ADMIN only)
POST /admin/certificates        - Generate certificate (ADMIN only)
GET  /admin/certificates/{id}/delete - Delete certificate (ADMIN only)
GET  /api/certificates          - List all certificates
POST /api/certificates          - Create certificate (API)
```

## 📝 Certificate Number Format

**SPPU-DOT-YYYYMMDDHHMMSS-XXXX**

- `SPPU-DOT` - University prefix
- `YYYYMMDDHHMMSS` - Timestamp (Year, Month, Day, Hour, Minute, Second)
- `XXXX` - Random 4-digit number

Example: `SPPU-DOT-20260227143025-7834`

## 🎨 Features in Detail

### Certificate Generation
- Unique number generation
- 🆕 Unique credential ID (SHA-256 based)
- 🆕 Verification hash for integrity
- 🆕 QR code generation (200x200px)
- Automatic timestamp
- SPPU branding
- Verification URL creation
- LinkedIn share URL generation
- Email notification (optional)

### Public Verification
- No login required
- Search by certificate number
- 🆕 Credential ID display for additional verification
- 🆕 QR code display for mobile scanning
- 🆕 Cryptographic verification hash
- Official certificate display
- Verification badge
- Print functionality
- LinkedIn sharing

### Admin Panel
- Certificate management
- User management
- Statistics dashboard
- Email status tracking
- Bulk operations

### Email Notifications
- HTML email template
- Certificate details
- Verification link
- LinkedIn share button
- SPPU branding

## 🐛 Troubleshooting

### Email Authentication Failed
```
⚠️ Email not configured. Skipping email for: recipient@example.com
```
**Solution:** This is normal. Email is optional. See EMAIL_SETUP.md to enable.

### Port 8080 Already in Use
```bash
netstat -ano | Select-String ":8080"
taskkill /F /PID <PID>
```

### MongoDB Not Running
```bash
# As Administrator
net start MongoDB
```

### Application Won't Start
1. Check MongoDB is running
2. Verify Java 25 is installed
3. Check port 8080 is available
4. Review console logs

## 📦 Project Structure

```
certificate-system/
├── src/
│   ├── main/
│   │   ├── java/com/certificate/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # Web controllers
│   │   │   ├── dto/             # Data transfer objects
│   │   │   ├── model/           # MongoDB entities
│   │   │   ├── repository/      # MongoDB repositories
│   │   │   ├── security/        # Security configuration
│   │   │   └── service/         # Business logic
│   │   └── resources/
│   │       ├── templates/       # Thymeleaf HTML templates
│   │       └── application.yaml # Configuration
│   └── test/                    # Test files
├── EMAIL_SETUP.md              # Email configuration guide
├── FEATURES_SUMMARY.md         # Feature documentation
├── QUICK_START.md              # Quick start guide
├── TESTING_GUIDE.md            # Testing instructions
├── setup-email.bat             # Email setup script
└── pom.xml                     # Maven dependencies
```

## 🚀 Production Deployment

For production:
1. Set `app.base-url` to your domain
2. Configure production email service
3. Enable HTTPS
4. Set up MongoDB authentication
5. Configure firewall rules
6. Set up automated backups
7. Monitor email delivery
8. Add rate limiting
9. Set up logging
10. Use environment variables for secrets

## 📄 License

© 2026 Savitribai Phule Pune University - Department of Technology

## 🤝 Support

For issues or questions:
1. Check documentation files
2. Verify MongoDB is running
3. Check console logs
4. Review QUICK_START.md

## ✅ Success Checklist

- [ ] MongoDB running
- [ ] Application started successfully
- [ ] Can login as admin
- [ ] Can generate certificates
- [ ] Can verify certificates publicly
- [ ] Certificate numbers start with SPPU-DOT-
- [ ] LinkedIn share button works
- [ ] Email configured (optional)

---

**Made with ❤️ for SPPU Department of Technology**
