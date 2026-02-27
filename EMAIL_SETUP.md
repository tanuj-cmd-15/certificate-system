# Email Configuration Guide

## Gmail Setup (Recommended for Testing)

### Step 1: Enable 2-Factor Authentication
1. Go to your Google Account: https://myaccount.google.com/
2. Navigate to Security
3. Enable 2-Step Verification

### Step 2: Generate App Password
1. Go to: https://myaccount.google.com/apppasswords
2. Select "Mail" and "Windows Computer" (or Other)
3. Click "Generate"
4. Copy the 16-character password

### Step 3: Configure Application
Update `src/main/resources/application.yaml`:

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-16-char-app-password
```

Or set environment variables:
```bash
export MAIL_USERNAME=your-email@gmail.com
export MAIL_PASSWORD=your-16-char-app-password
```

## Alternative: Use Other Email Providers

### Outlook/Hotmail
```yaml
spring:
  mail:
    host: smtp-mail.outlook.com
    port: 587
    username: your-email@outlook.com
    password: your-password
```

### Yahoo Mail
```yaml
spring:
  mail:
    host: smtp.mail.yahoo.com
    port: 587
    username: your-email@yahoo.com
    password: your-app-password
```

### Custom SMTP Server
```yaml
spring:
  mail:
    host: your-smtp-server.com
    port: 587
    username: your-username
    password: your-password
```

## Testing Email Without Configuration

If you don't configure email, the application will still work but:
- Certificates will be generated
- Email sending will fail silently
- Certificate status will show "Email Failed"
- Recipients won't receive email notifications

## Email Content

The email includes:
- Congratulations message
- Certificate details (number, course, issuer)
- Direct link to view certificate
- LinkedIn share button
- Verification instructions
- SPPU branding

## Troubleshooting

### "Authentication failed"
- Check username and password
- Ensure 2FA is enabled (Gmail)
- Use App Password, not regular password

### "Connection timeout"
- Check firewall settings
- Verify SMTP port (587 or 465)
- Try different port

### "Email not received"
- Check spam folder
- Verify recipient email address
- Check email service logs

## Production Recommendations

For production deployment:
1. Use dedicated email service (SendGrid, AWS SES, Mailgun)
2. Set up SPF, DKIM, DMARC records
3. Use environment variables for credentials
4. Monitor email delivery rates
5. Implement retry mechanism
6. Add email queue for bulk sending
