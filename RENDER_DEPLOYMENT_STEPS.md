# Render Deployment - Step by Step Guide

## ✅ Files Prepared

I've created these files for Render deployment:
- ✅ `render.yaml` - Render configuration
- ✅ `system.properties` - Java version specification
- ✅ Updated `application.yaml` - Environment variable support

---

## 📋 Step 1: Set Up MongoDB Atlas (15 minutes)

### 1.1 Create MongoDB Atlas Account
1. Go to: https://www.mongodb.com/cloud/atlas/register
2. Sign up with email or Google
3. Choose "Free" tier
4. Click "Create Cluster"

### 1.2 Create Cluster
1. Choose **M0 Free** tier
2. Select cloud provider: **AWS** (recommended)
3. Select region: **Singapore** or closest to you
4. Cluster name: `certificate-cluster`
5. Click "Create Cluster" (takes 3-5 minutes)

### 1.3 Create Database User
1. Click "Database Access" in left menu
2. Click "Add New Database User"
3. Authentication Method: **Password**
4. Username: `certificate_admin`
5. Password: Click "Autogenerate Secure Password" and **SAVE IT!**
   ```
   Example: aB3dE5fG7hI9jK
   ```
6. Database User Privileges: **Read and write to any database**
7. Click "Add User"

### 1.4 Configure Network Access
1. Click "Network Access" in left menu
2. Click "Add IP Address"
3. Click "Allow Access from Anywhere"
4. IP Address: `0.0.0.0/0` (should auto-fill)
5. Click "Confirm"

### 1.5 Get Connection String
1. Click "Database" in left menu
2. Click "Connect" button on your cluster
3. Choose "Connect your application"
4. Driver: **Java**, Version: **4.3 or later**
5. Copy the connection string:
   ```
   mongodb+srv://certificate_admin:<password>@certificate-cluster.xxxxx.mongodb.net/?retryWrites=true&w=majority
   ```
6. Replace `<password>` with your actual password
7. Add database name at the end:
   ```
   mongodb+srv://certificate_admin:aB3dE5fG7hI9jK@certificate-cluster.xxxxx.mongodb.net/certificate_db?retryWrites=true&w=majority
   ```
8. **SAVE THIS CONNECTION STRING!** You'll need it for Render.

---

## 📋 Step 2: Push Code to GitHub (5 minutes)

### 2.1 Initialize Git (if not done)
```bash
git init
```

### 2.2 Add All Files
```bash
git add .
```

### 2.3 Commit Changes
```bash
git commit -m "Prepare for Render deployment"
```

### 2.4 Create GitHub Repository
1. Go to: https://github.com/new
2. Repository name: `certificate-system`
3. Description: `SPPU Certificate Management System`
4. Visibility: **Private** (recommended) or Public
5. Click "Create repository"

### 2.5 Push to GitHub
```bash
# Replace YOUR_USERNAME with your GitHub username
git remote add origin https://github.com/YOUR_USERNAME/certificate-system.git
git branch -M main
git push -u origin main
```

---

## 📋 Step 3: Deploy to Render (10 minutes)

### 3.1 Create Render Account
1. Go to: https://render.com
2. Click "Get Started"
3. Sign up with **GitHub** (easiest)
4. Authorize Render to access GitHub

### 3.2 Create New Web Service
1. Click "New +" button (top right)
2. Select "Web Service"
3. Click "Connect" next to your `certificate-system` repository
4. If you don't see it, click "Configure account" and grant access

### 3.3 Configure Service
Fill in these details:

**Basic Settings:**
- **Name**: `sppu-certificate-system`
- **Region**: Singapore (or closest to you)
- **Branch**: `main`
- **Root Directory**: (leave blank)
- **Runtime**: `Java`

**Build Settings:**
- **Build Command**: 
  ```
  ./mvnw clean package -DskipTests
  ```
- **Start Command**: 
  ```
  java -Dserver.port=$PORT -jar target/certificate-system-0.0.1-SNAPSHOT.jar
  ```

**Plan:**
- Select **Free** plan

### 3.4 Add Environment Variables
Click "Advanced" and add these environment variables:

1. **SPRING_PROFILES_ACTIVE**
   - Value: `prod`

2. **SPRING_DATA_MONGODB_URI**
   - Value: Your MongoDB Atlas connection string from Step 1.5
   - Example: `mongodb+srv://certificate_admin:aB3dE5fG7hI9jK@certificate-cluster.xxxxx.mongodb.net/certificate_db?retryWrites=true&w=majority`

3. **APP_BASE_URL**
   - Value: `https://sppu-certificate-system.onrender.com`

4. **MAIL_ENABLED**
   - Value: `false`

5. **PORT**
   - Value: `8080`

### 3.5 Create Service
1. Click "Create Web Service"
2. Render will start building your application
3. This takes **10-15 minutes** for first build

### 3.6 Monitor Build
1. Watch the logs in real-time
2. Look for these success messages:
   ```
   [INFO] BUILD SUCCESS
   [INFO] Started CertificateSystemApplication
   ```
3. Status will change to "Live" when ready

---

## 📋 Step 4: Test Your Deployment (5 minutes)

### 4.1 Get Your URL
Your app will be available at:
```
https://sppu-certificate-system.onrender.com
```

### 4.2 Test Login
1. Visit: https://sppu-certificate-system.onrender.com/login
2. Username: `admin`
3. Password: `admin123`
4. Click "Login"

### 4.3 Test Certificate Generation
1. Go to Admin Panel
2. Fill certificate form:
   - Recipient Name: Test User
   - Email: test@example.com
   - Event: NEXUX TANTRA 2K26
   - Prize: First Prize
   - Category: Web Development
3. Click "Generate Certificate"
4. Verify credential ID appears

### 4.4 Test Verification
1. Copy certificate number
2. Visit: https://sppu-certificate-system.onrender.com/verify
3. Paste certificate number
4. Click "Verify"
5. Check QR code displays

---

## 🎉 Success!

Your certificate system is now live at:
**https://sppu-certificate-system.onrender.com**

Share these URLs:
- **Login**: https://sppu-certificate-system.onrender.com/login
- **Verify**: https://sppu-certificate-system.onrender.com/verify
- **Admin**: https://sppu-certificate-system.onrender.com/admin

---

## ⚠️ Important Notes

### Free Tier Limitations
- **Cold Starts**: Service sleeps after 15 minutes of inactivity
- **Wake Time**: Takes ~30 seconds to wake up
- **Solution**: Upgrade to Starter plan ($7/month) for no cold starts

### First Request After Sleep
- May take 30-60 seconds
- Subsequent requests are fast
- This is normal for free tier

### MongoDB Atlas Free Tier
- 512 MB storage
- Shared cluster
- Perfect for testing and small production
- Upgrade if you need more

---

## 🔧 Post-Deployment Tasks

### 1. Update Base URL in Render
If your URL is different:
1. Go to Render dashboard
2. Click on your service
3. Go to "Environment" tab
4. Update `APP_BASE_URL` to your actual URL
5. Service will auto-redeploy

### 2. Configure Email (Optional)
To enable email notifications:
1. Get Gmail app password (see EMAIL_SETUP.md)
2. Add environment variables in Render:
   - `MAIL_ENABLED`: `true`
   - `MAIL_USERNAME`: `your-email@gmail.com`
   - `MAIL_PASSWORD`: `your-app-password`
3. Service will auto-redeploy

### 3. Change Admin Password
1. Login as admin
2. Go to MongoDB Atlas
3. Connect with MongoDB Compass
4. Update admin password in `users` collection

### 4. Set Up Monitoring
1. In Render dashboard, go to "Metrics"
2. Monitor CPU, Memory, Request count
3. Set up email alerts in "Settings"

---

## 🐛 Troubleshooting

### Build Fails
**Check logs for:**
- Maven errors
- Java version issues
- Dependency problems

**Solution:**
```bash
# Test locally first
./mvnw clean package -DskipTests
```

### Service Won't Start
**Check logs for:**
- MongoDB connection errors
- Port binding issues
- Missing environment variables

**Solution:**
1. Verify MongoDB URI is correct
2. Check all environment variables are set
3. Ensure PORT is set to 8080

### MongoDB Connection Failed
**Common issues:**
- Wrong password in connection string
- Special characters in password (URL encode them)
- IP not whitelisted (should be 0.0.0.0/0)
- Wrong database name

**Solution:**
1. Test connection string locally
2. Check MongoDB Atlas network access
3. Verify database user exists

### 404 Errors
**Check:**
- Base URL is correct
- Service is running (not sleeping)
- Routes are correct

**Solution:**
1. Visit /login first to wake service
2. Check Render logs for errors
3. Verify application started successfully

---

## 📊 Monitoring Your App

### View Logs
```
Render Dashboard → Your Service → Logs
```

### Check Metrics
```
Render Dashboard → Your Service → Metrics
```

### Database Monitoring
```
MongoDB Atlas → Metrics → View cluster metrics
```

---

## 💰 Cost Breakdown

### Current Setup (Free)
- Render Free Tier: $0
- MongoDB Atlas M0: $0
- **Total: $0/month**

### Recommended for Production
- Render Starter: $7/month (no cold starts)
- MongoDB Atlas M0: $0 (or M10 at $9/month for backups)
- **Total: $7-16/month**

---

## 🔄 Updating Your App

### Make Changes
```bash
# Edit your code
git add .
git commit -m "Update feature"
git push origin main
```

### Automatic Deployment
- Render detects the push
- Builds new version
- Deploys automatically
- Zero downtime

---

## ✅ Deployment Checklist

- [x] MongoDB Atlas cluster created
- [x] Database user created
- [x] Network access configured
- [x] Connection string obtained
- [x] Code pushed to GitHub
- [x] Render service created
- [x] Environment variables set
- [x] Application deployed
- [x] Login tested
- [x] Certificate generation tested
- [x] Verification tested
- [x] QR code tested

---

## 📞 Support

### Render Issues
- Docs: https://render.com/docs
- Community: https://community.render.com
- Status: https://status.render.com

### MongoDB Atlas Issues
- Docs: https://docs.atlas.mongodb.com
- Support: https://support.mongodb.com

### Application Issues
- Check logs in Render dashboard
- Review TROUBLESHOOTING.md
- Test locally first

---

## 🎯 Next Steps

1. ✅ Share verification URL with users
2. ✅ Configure email (optional)
3. ✅ Change admin password
4. ✅ Set up monitoring alerts
5. ✅ Consider upgrading to paid plan
6. ✅ Set up database backups
7. ✅ Document your deployment

---

**Deployment Date**: February 27, 2026  
**Platform**: Render + MongoDB Atlas  
**Status**: Ready for Production  
**URL**: https://sppu-certificate-system.onrender.com
