# Quick Test Guide - Credential Verification System

## Prerequisites
✓ Application is running on `http://localhost:8080`
✓ MongoDB is running on `localhost:27017`
✓ Default admin account exists (username: `admin`, password: `admin123`)

## Test Steps

### Step 1: Login to Admin Panel
1. Open browser: `http://localhost:8080/login`
2. Enter credentials:
   - Username: `admin`
   - Password: `admin123`
3. Click "Login"
4. You should be redirected to dashboard

### Step 2: Navigate to Admin Panel
1. Click "Admin Panel" button on dashboard
2. OR directly visit: `http://localhost:8080/admin`
3. You should see:
   - Certificate generation form (left side)
   - List of all certificates (right side)
   - User statistics at top

### Step 3: Generate Test Certificate
Fill the form with test data:
```
Recipient Name: Rahul Sharma
Recipient Email: rahul.sharma@example.com
Event Name: NEXUX TANTRA 2K26
Prize/Position: First Prize
Category: Web Development
Issuer Name: (leave blank for default)
```

Click "Generate Certificate"

### Step 4: Verify Certificate Created
After generation, check the certificates table:
- ✓ Certificate Number appears (e.g., `SPPU-DOT-20260227113456-1234`)
- ✓ **Credential ID appears** (e.g., `CRED-A1B2C3D4E5F6G7H8`)
- ✓ Recipient name shows: Rahul Sharma
- ✓ Email shows: rahul.sharma@example.com
- ✓ Email Sent status (✓ Sent or ✗ Failed - both OK for testing)
- ✓ Issue date shows today's date

### Step 5: Open Certificate Verification
1. Click on the certificate number link in the table
2. OR copy certificate number and visit: `http://localhost:8080/verify`
3. Paste certificate number in search box
4. Click "Verify" button

### Step 6: Verify All New Features
On the verification page, confirm you see:

#### ✓ Verification Badge
- Green box with "✓ VERIFIED CERTIFICATE"
- Text: "This is an authentic certificate issued by SPPU Department of Technology"
- **Credential ID displayed** below verification message

#### ✓ Certificate Details Box
- Certificate Number: `SPPU-DOT-XXXXXXXX-XXXX`
- **Credential ID: `CRED-XXXXXXXXXXXXXXXX`** (NEW!)

#### ✓ QR Code Section
- QR code image (200x200 pixels)
- Text below: "Scan to verify certificate"
- QR code should be clear and scannable

#### ✓ Certificate Content
- Recipient name: Rahul Sharma
- Event badge: NEXUX TANTRA 2K26
- Prize text: "First Prize"
- Category text: "Web Development"
- Date: Today's date
- Signature: DR. ADITYA ABHYANKAR

### Step 7: Test QR Code (Optional)
1. Use your phone's camera or QR scanner app
2. Scan the QR code from the screen
3. Your phone should open the verification URL
4. Confirm it shows the same certificate

### Step 8: Test LinkedIn Share
1. Click "Share on LinkedIn" button
2. LinkedIn should open with pre-filled certification form
3. Verify the details are correct
4. (Don't actually post unless you want to)

### Step 9: Test Print Function
1. Click "Print Certificate" button
2. Print preview should open
3. Verify:
   - QR code is hidden in print view
   - Credential ID box is hidden in print view
   - Certificate looks clean and professional
   - Only the certificate design is visible

### Step 10: Test Search Functionality
1. Go back to: `http://localhost:8080/verify`
2. Try searching with partial certificate number
3. Try searching with wrong certificate number
4. Verify error message appears for invalid certificates

## Expected Results

### ✓ Success Indicators
- Certificate generates with unique credential ID
- Credential ID is 16 hexadecimal characters after "CRED-"
- QR code displays as a clear image
- QR code scans and opens verification page
- All certificate details are correct
- Verification badge shows green
- Print view hides web-only elements

### ✗ Common Issues

#### Issue: QR Code Not Showing
**Solution**: 
- Check browser console for errors
- Verify ZXing library is loaded
- Restart application

#### Issue: Credential ID Shows "null"
**Solution**:
- Certificate was created before credential feature
- Delete old certificates and create new ones
- Check `generateCredentialId()` method is called

#### Issue: Email Shows "✗ Failed"
**Solution**:
- This is normal if email is not configured
- Email is optional for testing
- See `EMAIL_SETUP.md` to configure email

## Database Verification

### Check MongoDB Data
Open MongoDB shell or Compass and run:
```javascript
use certificate_db
db.certificates.find().pretty()
```

Verify each certificate has:
- `certificateNumber`: String (unique)
- `credentialId`: String starting with "CRED-"
- `verificationHash`: Long hexadecimal string
- `qrCodeData`: String starting with "data:image/png;base64,"
- `recipientName`: String
- `recipientEmail`: String
- `prize`: String
- `category`: String
- `issuedDate`: ISODate

## Performance Test

### Generate Multiple Certificates
1. Create 5-10 certificates with different data
2. Verify each has unique:
   - Certificate number
   - Credential ID
   - QR code
3. Check admin table loads quickly
4. Verify search works for all certificates

## Security Test

### Verify Uniqueness
1. Generate two certificates with same recipient
2. Confirm they have different:
   - Certificate numbers
   - Credential IDs
   - QR codes
3. Verify both can be verified independently

### Test Invalid Access
1. Try accessing non-existent certificate: `http://localhost:8080/verify/INVALID-NUMBER`
2. Should show error: "Certificate Not Found"
3. Try accessing admin panel without login
4. Should redirect to login page

## Cleanup (Optional)

### Delete Test Certificates
1. In admin panel, click "Delete" on test certificates
2. Confirm deletion
3. Verify certificate is removed from list
4. Try verifying deleted certificate (should fail)

## Next Steps

After successful testing:
1. ✓ Create real certificates for actual recipients
2. ✓ Configure email (see `EMAIL_SETUP.md`)
3. ✓ Share verification URL with stakeholders
4. ✓ Train staff on certificate generation
5. ✓ Document credential IDs for records

## Troubleshooting Commands

### Check Application Status
```bash
# Check if application is running
curl http://localhost:8080/login

# Check MongoDB connection
mongo --eval "db.adminCommand('ping')"
```

### View Application Logs
```bash
# In project directory
tail -f target/logs/spring.log
```

### Restart Application
```bash
# Stop application (Ctrl+C in terminal)
# Then restart
./mvnw spring-boot:run
```

## Support Checklist

Before asking for help, verify:
- [ ] Application is running (check terminal)
- [ ] MongoDB is running (check services)
- [ ] Browser console has no errors (F12)
- [ ] Certificate was created after credential feature
- [ ] Using correct certificate number format
- [ ] Network connection is stable

---

**Test Status**: Ready for Testing
**Last Updated**: February 27, 2026
**Estimated Test Time**: 10-15 minutes
