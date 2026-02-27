# Render Deployment Checklist

Use this checklist to track your deployment progress.

---

## 📋 Part 1: MongoDB Atlas Setup

### Account & Cluster
- [ ] Created MongoDB Atlas account
- [ ] Created M0 Free cluster
- [ ] Cluster is active (green status)
- [ ] Cluster name: `certificate-cluster`

### Database User
- [ ] Created database user
- [ ] Username: `certificate_admin`
- [ ] Password saved securely
- [ ] Privileges: Read and write to any database

### Network Access
- [ ] Added IP address
- [ ] Set to: `0.0.0.0/0` (Allow from anywhere)
- [ ] Status shows "Active"

### Connection String
- [ ] Obtained connection string
- [ ] Replaced `<password>` with actual password
- [ ] Added database name: `/certificate_db`
- [ ] Final format: `mongodb+srv://certificate_admin:PASSWORD@cluster.xxxxx.mongodb.net/certificate_db?retryWrites=true&w=majority`
- [ ] Connection string saved securely

**Your MongoDB URI:**
```
mongodb+srv://certificate_admin:___________@certificate-cluster._____.mongodb.net/certificate_db?retryWrites=true&w=majority
```

---

## 📋 Part 2: GitHub Setup

### Repository
- [ ] Git initialized (`git init`)
- [ ] All files added (`git add .`)
- [ ] Changes committed (`git commit -m "Prepare for Render deployment"`)
- [ ] GitHub repository created
- [ ] Repository name: `certificate-system`
- [ ] Remote added (`git remote add origin ...`)
- [ ] Code pushed to GitHub (`git push -u origin main`)

**Your GitHub Repository:**
```
https://github.com/___________/certificate-system
```

---

## 📋 Part 3: Render Setup

### Account
- [ ] Created Render account
- [ ] Signed up with GitHub
- [ ] Authorized Render to access GitHub

### Web Service
- [ ] Created new web service
- [ ] Connected GitHub repository
- [ ] Service name: `sppu-certificate-system`
- [ ] Region selected: ___________
- [ ] Branch: `main`
- [ ] Runtime: Java

### Build Configuration
- [ ] Build command set: `./mvnw clean package -DskipTests`
- [ ] Start command set: `java -Dserver.port=$PORT -jar target/certificate-system-0.0.1-SNAPSHOT.jar`
- [ ] Plan: Free

### Environment Variables
- [ ] `SPRING_PROFILES_ACTIVE` = `prod`
- [ ] `SPRING_DATA_MONGODB_URI` = (your MongoDB URI)
- [ ] `APP_BASE_URL` = `https://sppu-certificate-system.onrender.com`
- [ ] `MAIL_ENABLED` = `false`
- [ ] `PORT` = `8080`

### Deployment
- [ ] Clicked "Create Web Service"
- [ ] Build started
- [ ] Build completed successfully
- [ ] Service status: "Live"

**Your Render URL:**
```
https://sppu-certificate-system.onrender.com
```

---

## 📋 Part 4: Testing

### Basic Access
- [ ] Can access login page
- [ ] URL: https://sppu-certificate-system.onrender.com/login
- [ ] Page loads without errors
- [ ] HTTPS is working (green padlock)

### Admin Login
- [ ] Can login with admin/admin123
- [ ] Redirected to dashboard
- [ ] No errors in browser console (F12)

### Certificate Generation
- [ ] Can access admin panel
- [ ] Can fill certificate form
- [ ] Certificate generates successfully
- [ ] Credential ID appears in table
- [ ] Certificate number is unique

### Verification
- [ ] Can access verification page
- [ ] Can search by certificate number
- [ ] Certificate displays correctly
- [ ] Credential ID shows
- [ ] QR code displays
- [ ] Green verification badge shows

### QR Code
- [ ] QR code is visible
- [ ] QR code is scannable with phone
- [ ] Scanning opens verification page
- [ ] Correct certificate displays

### Print Function
- [ ] Print preview works
- [ ] Certificate looks professional
- [ ] QR code hidden in print
- [ ] Credential box hidden in print

---

## 📋 Part 5: Post-Deployment

### Security
- [ ] Changed admin password (or planned to)
- [ ] MongoDB password is strong
- [ ] Environment variables are secure
- [ ] No sensitive data in GitHub

### Monitoring
- [ ] Checked Render logs
- [ ] No errors in logs
- [ ] Application started successfully
- [ ] MongoDB connection successful

### Documentation
- [ ] Saved MongoDB URI securely
- [ ] Saved Render URL
- [ ] Documented admin credentials
- [ ] Noted any issues encountered

### Optional Features
- [ ] Email configured (if needed)
- [ ] Custom domain added (if needed)
- [ ] Monitoring alerts set up
- [ ] Database backups configured

---

## 📋 Part 6: Sharing

### URLs to Share
- [ ] Login URL: https://sppu-certificate-system.onrender.com/login
- [ ] Verify URL: https://sppu-certificate-system.onrender.com/verify
- [ ] Admin URL: https://sppu-certificate-system.onrender.com/admin

### Credentials to Share
- [ ] Admin username: `admin`
- [ ] Admin password: `admin123` (change this!)
- [ ] Verification URL for public use

### Documentation to Share
- [ ] How to verify certificates
- [ ] How to generate certificates
- [ ] Support contact information

---

## ✅ Final Verification

### All Systems Go
- [ ] Application is live
- [ ] MongoDB is connected
- [ ] Certificates can be generated
- [ ] Certificates can be verified
- [ ] QR codes work
- [ ] No critical errors
- [ ] Performance is acceptable
- [ ] Ready for production use

---

## 🎉 Deployment Complete!

**Deployment Date:** ___________  
**Deployed By:** ___________  
**Platform:** Render + MongoDB Atlas  
**Status:** ✅ Live

**Application URL:**
```
https://sppu-certificate-system.onrender.com
```

**Next Steps:**
1. Share verification URL with users
2. Train staff on certificate generation
3. Monitor application performance
4. Plan for scaling if needed
5. Set up regular backups

---

## 📞 Emergency Contacts

### If Something Goes Wrong

**Render Issues:**
- Check: https://status.render.com
- Logs: Render Dashboard → Your Service → Logs
- Support: https://community.render.com

**MongoDB Issues:**
- Check: MongoDB Atlas Dashboard
- Metrics: Atlas → Metrics
- Support: https://support.mongodb.com

**Application Issues:**
- Check: Render logs
- Review: TROUBLESHOOTING.md
- Test: Run locally to reproduce

---

## 💡 Tips

### Cold Starts (Free Tier)
- First request after 15 min takes ~30 seconds
- This is normal for free tier
- Upgrade to $7/month to eliminate cold starts

### Performance
- Monitor response times in Render metrics
- Check MongoDB Atlas metrics
- Consider upgrading if needed

### Costs
- Current: $0/month (free tier)
- Recommended: $7/month (no cold starts)
- With backups: $16/month (Render + MongoDB M10)

---

**Checklist Version:** 1.0  
**Last Updated:** February 27, 2026  
**Status:** Ready to Use
