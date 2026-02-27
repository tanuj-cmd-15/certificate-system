# Quick Start Guide - SPPU Certificate System

## 🚀 Getting Started (3 Steps)

### Step 1: Start MongoDB
MongoDB should already be running. Verify with:
```bash
tasklist | findstr mongod
```

If not running, start it:
```bash
# Open Command Prompt as Administrator
net start MongoDB
```

### Step 2: Configure Email (Optional)

**Option A: Skip Email (Recommended for Testing)**
- Application works without email
- Certificates are generated normally
- Email status shows "Failed"
- All other features work perfectly

**Option B: Enable Email**

Run the setup script:
```bash
setup-email.bat
```

Or manually set environment variables:
```bash
# Windows Command Prompt
setx MAIL_USERNAME "your-email@gmail.com"
setx MAIL_PASSWORD "your-16-char-app-password"
setx MAIL_ENABLED "true"
```

**To get Gmail App Password:**
1. Enable 2FA: https://myaccount.google.com/security
2. Generate App Password: https://myaccount.google.com/apppasswords
3. Select "Mail" and "Windows Computer"
4. Copy the 16-character password

### Step 3: Start Application
```bash
./mvnw spring-boot:run -DskipTests
```

Wait for: `Started CertificateSystemApplication`

## 🎯 Access the Application

### Main URLs
- **Login:** http://localhost:8080/login
- **Signup:** http://localhost:8080/signup
- **Verify Certificate:** http://localhost:8080/verify (public, no login)

### Default Admin Account
- **Username:** admin
- **Password:** admin123

## ✅ Quick Test

1. **Login as Admin**
   - Go to: http://localhost:8080/login
   - Username: admin
   - Password: admin123

2. **Generate Certificate**
   - Click "Admin Panel"
   - Fill the form:
     - Recipient Name: Test User
     - Recipient Email: test@example.com
     - Course Name: Web Development
   - Click "Generate Certificate"

3. **Verify Certificate**
   - Copy the certificate number (e.g., SPPU-DOT-20260227...)
   - Open new tab: http://localhost:8080/verify
   - Paste certificate number
   - Click "Verify"
   - See official SPPU certificate!

4. **Share on LinkedIn**
   - From verification page
   - Click "Share on LinkedIn"
   - LinkedIn opens with pre-filled data

## 📧 Email Status

### Without Email Configuration
```
⚠️  Email not configured. Skipping email for: recipient@example.com
📝 To enable email, see EMAIL_SETUP.md
```
- This is normal and expected
- Certificate is still created
- All features work except email

### With Email Configuration
```
✅ Email sent successfully to: recipient@example.com
```
- Recipient receives email
- Email includes verification link
- LinkedIn share button included

## 🔧 Troubleshooting

### "Email not configured" message
- This is normal if you haven't set up email
- Application works perfectly without email
- To enable: Run `setup-email.bat` or see EMAIL_SETUP.md

### "Authentication failed"
- Check your Gmail App Password (not regular password)
- Ensure 2FA is enabled on Google Account
- Verify environment variables are set
- Restart application after setting variables

### Port 8080 already in use
```bash
# Find process using port 8080
netstat -ano | Select-String ":8080"

# Kill the process (replace PID with actual number)
taskkill /F /PID <PID>
```

### MongoDB not running
```bash
# Start MongoDB (as Administrator)
net start MongoDB
```

## 📚 Documentation

- **TESTING_GUIDE.md** - Complete testing instructions
- **EMAIL_SETUP.md** - Detailed email configuration
- **FEATURES_SUMMARY.md** - All features explained

## 🎓 Key Features

✅ Unique certificate numbers (SPPU-DOT-...)
✅ Public verification (no login required)
✅ LinkedIn integration
✅ Email notifications (optional)
✅ SPPU official branding
✅ Admin panel for management
✅ Searchable certificates
✅ Print-friendly design

## 💡 Tips

1. **Email is Optional** - Don't worry if email doesn't work initially
2. **Test Verification First** - This is the most important feature
3. **Use Admin Account** - Username: admin, Password: admin123
4. **Public Verification** - Share http://localhost:8080/verify with anyone
5. **Certificate Numbers** - Always start with SPPU-DOT-

## 🆘 Need Help?

1. Check if MongoDB is running
2. Verify application started successfully
3. Try accessing http://localhost:8080/verify (should work without login)
4. Check console for error messages
5. Email issues? See EMAIL_SETUP.md

## ✨ Success Indicators

You'll know everything is working when:
- ✅ Can login at http://localhost:8080/login
- ✅ Can generate certificates in admin panel
- ✅ Can verify certificates at http://localhost:8080/verify
- ✅ Certificate numbers start with SPPU-DOT-
- ✅ LinkedIn share button appears on verification page

Email working is a bonus, not required!
