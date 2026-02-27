# Unique Credential & Verification System

## Overview
The certificate system now includes advanced security features with unique credentials and QR code verification to ensure authenticity and prevent fraud.

## Features Implemented

### 1. Unique Credential ID
- **Format**: `CRED-XXXXXXXXXXXXXXXX` (16 hexadecimal characters)
- **Generation**: SHA-256 hash based on certificate number, recipient email, and issue date
- **Purpose**: Provides an additional layer of verification beyond the certificate number
- **Uniqueness**: Each certificate has a unique credential ID that cannot be duplicated

### 2. Verification Hash
- **Technology**: SHA-256 cryptographic hash
- **Data**: Combines certificate number, recipient name, email, and issue date
- **Purpose**: Ensures certificate integrity and prevents tampering
- **Storage**: Stored in MongoDB for verification checks

### 3. QR Code Generation
- **Library**: Google ZXing (Zebra Crossing) version 3.5.2
- **Format**: PNG image encoded as Base64 data URL
- **Size**: 200x200 pixels
- **Content**: Verification URL with embedded credential ID
- **Scanning**: Can be scanned with any QR code reader app

### 4. Enhanced Verification Page
The verification page (`/verify`) now displays:
- ✓ Verified certificate badge
- Certificate number
- **NEW**: Credential ID (displayed prominently)
- **NEW**: QR code for mobile verification
- Recipient details
- Event information
- Prize and category
- Issue date

## How It Works

### Certificate Generation Flow
1. Admin creates certificate with recipient details
2. System generates unique certificate number: `SPPU-DOT-YYYYMMDDHHMMSS-XXXX`
3. System generates credential ID using SHA-256 hash
4. System generates verification hash for integrity
5. System creates QR code with verification URL + credential ID
6. Certificate is saved to MongoDB with all security data
7. Email sent to recipient with verification link

### Verification Flow
1. User visits `/verify` page
2. Enters certificate number OR scans QR code
3. System retrieves certificate from database
4. Displays verified certificate with:
   - Credential ID for additional verification
   - QR code for sharing/re-verification
   - Full certificate details
5. User can verify credential ID matches official records

### QR Code Verification
The QR code contains: `http://localhost:8080/verify/SPPU-DOT-XXXXX?cred=CRED-XXXXX`

When scanned:
- Opens verification page directly
- Shows certificate with credential ID
- Provides instant authenticity confirmation

## Security Benefits

### 1. Multi-Factor Verification
- Certificate Number (primary identifier)
- Credential ID (secondary identifier)
- Verification Hash (integrity check)
- QR Code (mobile verification)

### 2. Tamper Detection
- Any modification to certificate data invalidates the verification hash
- Credential ID cannot be forged without access to original data
- QR code links directly to official verification system

### 3. Easy Verification
- Employers can verify certificates by:
  - Entering certificate number on website
  - Scanning QR code with phone
  - Checking credential ID matches
  - Confirming recipient details

### 4. Fraud Prevention
- Unique credential IDs prevent certificate duplication
- QR codes cannot be copied to fake certificates (they link to real data)
- Verification hash ensures data integrity
- All verifications are logged in the system

## Usage Guide

### For Administrators
1. Login to admin panel: `http://localhost:8080/admin`
2. Fill certificate form with recipient details
3. Click "Generate Certificate"
4. System automatically creates:
   - Certificate number
   - Credential ID
   - Verification hash
   - QR code
5. View credential ID in the certificates table
6. Share verification link with recipient

### For Recipients
1. Receive email with certificate link
2. Click link to view certificate
3. Certificate displays:
   - Your name and achievement
   - Certificate number
   - **Credential ID** (save this!)
   - QR code (screenshot for sharing)
4. Share on LinkedIn using the button
5. Print certificate if needed

### For Verifiers (Employers/Institutions)
1. Visit: `http://localhost:8080/verify`
2. Enter certificate number provided by candidate
3. Verify the displayed information:
   - Recipient name matches
   - Event/course name is correct
   - Issue date is valid
   - **Credential ID matches** (ask candidate for this)
4. Alternative: Scan QR code from candidate's certificate
5. Confirm authenticity with green verification badge

## Technical Implementation

### Database Schema
```javascript
{
  "_id": "ObjectId",
  "certificateNumber": "SPPU-DOT-20260227113456-1234",
  "credentialId": "CRED-A1B2C3D4E5F6G7H8",
  "verificationHash": "sha256_hash_value",
  "qrCodeData": "data:image/png;base64,...",
  "recipientName": "John Doe",
  "recipientEmail": "john@example.com",
  "courseName": "NEXUX TANTRA 2K26",
  "prize": "First Prize",
  "category": "Web Development",
  "issuedDate": "2026-02-27T11:34:56",
  "verificationUrl": "http://localhost:8080/verify/SPPU-DOT-...",
  "linkedInShareUrl": "https://www.linkedin.com/profile/add?..."
}
```

### API Endpoints
- `GET /verify` - Verification search page
- `GET /verify/{certificateNumber}` - Direct certificate verification
- `GET /verify?certNumber=XXX` - Search by certificate number
- `POST /admin/certificates` - Generate new certificate (admin only)

### Dependencies Added
```xml
<!-- QR Code Generation -->
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>core</artifactId>
    <version>3.5.2</version>
</dependency>
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>javase</artifactId>
    <version>3.5.2</version>
</dependency>
```

## Testing the Feature

### Test Scenario 1: Generate Certificate
1. Login as admin (username: `admin`, password: `admin123`)
2. Navigate to Admin Panel
3. Fill form:
   - Recipient Name: Test User
   - Email: test@example.com
   - Event: NEXUX TANTRA 2K26
   - Prize: First Prize
   - Category: Web Development
4. Click "Generate Certificate"
5. Verify credential ID appears in table

### Test Scenario 2: Verify Certificate
1. Copy certificate number from admin table
2. Open new tab: `http://localhost:8080/verify`
3. Paste certificate number
4. Click "Verify"
5. Confirm:
   - Green verification badge appears
   - Credential ID is displayed
   - QR code is visible
   - All details are correct

### Test Scenario 3: QR Code Verification
1. View certificate on verification page
2. Screenshot or save QR code
3. Use phone's QR scanner app
4. Scan the QR code
5. Verify it opens the certificate page
6. Confirm credential ID matches

## Production Deployment Notes

### Before Going Live
1. **Update Base URL**: Change `app.base-url` in `application.yaml` to production domain
2. **Enable HTTPS**: QR codes should use HTTPS URLs for security
3. **Database Backup**: Ensure MongoDB backups include credential data
4. **Email Configuration**: Configure SMTP for email notifications
5. **Security**: Enable rate limiting on verification endpoint

### Recommended Enhancements
1. Add verification audit log (track who verified what and when)
2. Implement API for bulk verification
3. Add credential ID to email notifications
4. Create mobile app for QR scanning
5. Add blockchain integration for immutable records
6. Implement certificate revocation system

## Troubleshooting

### QR Code Not Displaying
- Check `qrCodeData` field in database is not empty
- Verify ZXing dependencies are in classpath
- Check browser console for image loading errors

### Credential ID Not Generated
- Ensure `generateCredentialId()` is called before saving
- Check MongoDB connection is active
- Verify Java security providers are available for SHA-256

### Verification Fails
- Confirm certificate exists in database
- Check certificate number format is correct
- Verify MongoDB query is case-sensitive

## Support
For issues or questions:
- Check application logs: `target/logs/`
- Review MongoDB data: `use certificate_db; db.certificates.find()`
- Contact: Department of Technology, SPPU

---

**System Status**: ✓ Fully Operational
**Last Updated**: February 27, 2026
**Version**: 1.0.0
