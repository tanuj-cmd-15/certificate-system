# Troubleshooting Guide

## Certificate Generation Not Working

### Step 1: Verify You're Logged In as Admin

1. Go to: http://localhost:8080/login
2. Enter credentials:
   - Username: `admin`
   - Password: `admin123`
3. Click "Login"
4. You should be redirected to Dashboard
5. Click "Admin Panel" in the navbar

### Step 2: Fill the Certificate Form Correctly

**Required Fields:**
- ✅ Recipient Name (e.g., "John Doe")
- ✅ Recipient Email (e.g., "john@example.com")
- ✅ Event Name (e.g., "NEXUX TANTRA 2K26")

**Optional Fields:**
- Prize/Position (select from dropdown or leave empty)
- Category (e.g., "Web Development" or leave empty)
- Issuer Name (leave empty for default "SPPU DOT")

### Step 3: Click "Generate Certificate"

After clicking, you should:
1. See "Certificate created successfully!" message
2. Certificate appears in the table below
3. Email status shows "Sent" or "Failed"

### Common Issues and Solutions

#### Issue 1: "Whitelabel Error Page"
**Cause:** Not logged in or session expired
**Solution:**
1. Go to http://localhost:8080/login
2. Login again with admin/admin123
3. Try generating certificate again

#### Issue 2: Form Validation Errors
**Symptoms:** Red error messages under fields
**Solution:**
- Check all required fields are filled
- Email must be valid format (contains @)
- Name must be at least 3 characters

#### Issue 3: "Email not configured" in console
**This is NORMAL!**
- Certificate is still generated
- Email status shows "Failed"
- All other features work
- To enable email, see EMAIL_SETUP.md

#### Issue 4: Certificate Not Appearing in Table
**Solution:**
1. Refresh the page (F5)
2. Check MongoDB is running: `tasklist | findstr mongod`
3. Check console logs for errors

#### Issue 5: Can't Access Admin Panel
**Cause:** Not logged in as admin
**Solution:**
1. Logout if logged in as regular user
2. Login with admin/admin123
3. Admin Panel link should appear in navbar

### Testing Certificate Generation

**Test 1: Basic Certificate**
```
Recipient Name: Test User
Recipient Email: test@example.com
Event Name: NEXUX TANTRA 2K26
Prize: (leave empty)
Category: (leave empty)
Issuer Name: (leave empty)
```
Click "Generate Certificate"

**Test 2: Full Certificate with Prize**
```
Recipient Name: Rahul Sharma
Recipient Email: rahul@example.com
Event Name: NEXUX TANTRA 2K26
Prize: First Prize
Category: Web Development
Issuer Name: (leave empty)
```
Click "Generate Certificate"

**Test 3: Participation Certificate**
```
Recipient Name: Priya Patel
Recipient Email: priya@example.com
Event Name: NEXUX TANTRA 2K26
Prize: Participation
Category: AI/ML
Issuer Name: (leave empty)
```
Click "Generate Certificate"

### Verifying Certificate Was Created

After generating, you should see:
1. ✅ Success message at top of page
2. ✅ Certificate in the table with:
   - Certificate Number (SPPU-DOT-...)
   - Recipient name and email
   - Event name
   - Email status (Sent/Failed)
   - Issue date
   - Delete button

3. ✅ Click certificate number to view certificate
4. ✅ Certificate opens in new tab with green design

### Checking Console Logs

Look for these messages in console:

**Success:**
```
✅ Email sent successfully to: recipient@example.com
```
OR
```
⚠️  Email not configured. Skipping email for: recipient@example.com
📝 To enable email, see EMAIL_SETUP.md
```

**Error:**
```
❌ Failed to send email: [error message]
```

### MongoDB Issues

**Check if MongoDB is running:**
```bash
tasklist | findstr mongod
```

**Should see:**
```
mongod.exe    [PID] Services    0    [Memory]
```

**If not running:**
```bash
# Open Command Prompt as Administrator
net start MongoDB
```

### Application Not Starting

**Check port 8080:**
```bash
netstat -ano | Select-String ":8080"
```

**If port is in use:**
```bash
# Find PID from above command
taskkill /F /PID [PID]
```

**Restart application:**
```bash
./mvnw spring-boot:run -DskipTests
```

### Browser Issues

**Clear browser cache:**
1. Press Ctrl+Shift+Delete
2. Clear cached images and files
3. Refresh page (Ctrl+F5)

**Try incognito/private mode:**
1. Ctrl+Shift+N (Chrome)
2. Ctrl+Shift+P (Firefox)
3. Login and test again

### Database Issues

**Check MongoDB connection:**
Look for this in console logs:
```
Monitor thread successfully connected to server
```

**If connection fails:**
1. Verify MongoDB is running
2. Check MongoDB is on port 27017
3. Restart MongoDB service
4. Restart application

### Form Submission Issues

**Check browser console (F12):**
1. Open Developer Tools (F12)
2. Go to Console tab
3. Try submitting form
4. Look for JavaScript errors

**Check Network tab:**
1. Open Developer Tools (F12)
2. Go to Network tab
3. Submit form
4. Check POST request to /admin/certificates
5. Look at response status (should be 302 redirect)

### Still Not Working?

1. **Restart everything:**
   ```bash
   # Stop application (Ctrl+C in terminal)
   # Restart MongoDB
   net stop MongoDB
   net start MongoDB
   # Start application
   ./mvnw spring-boot:run -DskipTests
   ```

2. **Check application logs:**
   - Look for red ERROR messages in console
   - Look for stack traces
   - Note the error message

3. **Verify URLs:**
   - Login: http://localhost:8080/login
   - Admin: http://localhost:8080/admin
   - Verify: http://localhost:8080/verify

4. **Test with curl:**
   ```bash
   # Should redirect to login
   curl http://localhost:8080/admin
   ```

### Success Checklist

- [ ] MongoDB is running
- [ ] Application started successfully
- [ ] Logged in as admin (admin/admin123)
- [ ] Can see Admin Panel link in navbar
- [ ] Admin Panel page loads
- [ ] Certificate form is visible
- [ ] Can fill all required fields
- [ ] "Generate Certificate" button works
- [ ] Success message appears
- [ ] Certificate appears in table
- [ ] Can click certificate number
- [ ] Certificate displays with green design
- [ ] Can print certificate
- [ ] LinkedIn share button works

### Quick Test Command

After logging in as admin, try this in browser console (F12):
```javascript
// Check if form exists
document.querySelector('form[action="/admin/certificates"]')

// Check if submit button exists
document.querySelector('button[type="submit"]')
```

Both should return HTML elements, not null.

### Contact Information

If still having issues:
1. Check all documentation files
2. Review console logs carefully
3. Verify all prerequisites are met
4. Try the test cases above

---

**Remember:** Email not working is NORMAL and expected if you haven't configured it. The certificate system works perfectly without email!
