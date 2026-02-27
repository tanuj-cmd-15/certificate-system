# Deploy to Railway - Complete Guide

## 🚂 Why Railway?

Railway is the easiest way to deploy your Spring Boot application with MongoDB:
- ✅ Free tier available ($5 credit/month)
- ✅ Automatic MongoDB provisioning
- ✅ Git-based deployment
- ✅ HTTPS included
- ✅ Environment variables management
- ✅ Automatic builds and deployments

---

## 📋 Prerequisites

1. GitHub account
2. Railway account (sign up at https://railway.app)
3. Your code pushed to GitHub

---

## 🚀 Step-by-Step Deployment

### Step 1: Push Code to GitHub

```bash
# Initialize git (if not already done)
git init

# Add all files
git add .

# Commit
git commit -m "Initial commit - SPPU Certificate System"

# Create repository on GitHub, then:
git remote add origin https://github.com/YOUR_USERNAME/certificate-system.git
git branch -M main
git push -u origin main
```

### Step 2: Sign Up for Railway

1. Go to https://railway.app
2. Click "Login" → "Login with GitHub"
3. Authorize Railway to access your GitHub

### Step 3: Create New Project

1. Click "New Project"
2. Select "Deploy from GitHub repo"
3. Choose your `certificate-system` repository
4. Railway will detect it's a Spring Boot app

### Step 4: Add MongoDB Database

1. In your Railway project dashboard
2. Click "New" → "Database" → "Add MongoDB"
3. Railway will provision a MongoDB instance
4. Note: MongoDB will be automatically connected

### Step 5: Configure Environment Variables

1. Click on your Spring Boot service
2. Go to "Variables" tab
3. Add these variables:

```
SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/certificate_db
SPRING_PROFILES_ACTIVE=prod
APP_BASE_URL=https://your-app.railway.app
MAIL_ENABLED=false
```

**Note**: Railway automatically provides `MONGO_URL` variable. We'll use it.

### Step 6: Update application.yaml

Add production profile to `src/main/resources/application.yaml`:

```yaml
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:default}
  data:
    mongodb:
      uri: ${MONGODB_URI:mongodb://localhost:27017/certificate_db}
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME:}
    password: ${MAIL_PASSWORD:}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
    enabled: ${MAIL_ENABLED:false}

server:
  port: ${PORT:8080}

app:
  base-url: ${APP_BASE_URL:http://localhost:8080}
  certificate:
    prefix: SPPU-DOT
    issuer: ${CERTIFICATE_ISSUER:DR. ADITYA ABHYANKAR, HEAD OF DEPARTMENT}

---
# Production profile
spring:
  config:
    activate:
      on-profile: prod
  data:
    mongodb:
      uri: ${MONGODB_URI}
```

### Step 7: Create Procfile (Optional)

Create `Procfile` in root directory:

```
web: java -jar target/certificate-system-0.0.1-SNAPSHOT.jar
```

### Step 8: Update pom.xml for Production

Ensure your `pom.xml` has the correct build configuration:

```xml
<build>
    <finalName>certificate-system</finalName>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludes>
                    <exclude>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                    </exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Step 9: Push Changes

```bash
git add .
git commit -m "Configure for Railway deployment"
git push origin main
```

### Step 10: Deploy

1. Railway will automatically detect the push
2. It will build your application
3. Wait for deployment to complete (5-10 minutes)
4. Click "View Logs" to monitor progress

### Step 11: Get Your URL

1. Go to "Settings" tab
2. Under "Domains", click "Generate Domain"
3. Your app will be available at: `https://your-app.railway.app`

---

## 🔧 Post-Deployment Configuration

### Update Base URL

1. Go to Variables tab
2. Update `APP_BASE_URL` to your Railway domain:
   ```
   APP_BASE_URL=https://your-app.railway.app
   ```
3. Redeploy (Railway will auto-redeploy)

### Configure Email (Optional)

1. Add email variables:
   ```
   MAIL_ENABLED=true
   MAIL_USERNAME=your-email@gmail.com
   MAIL_PASSWORD=your-app-password
   ```
2. Redeploy

### Create Admin User

Railway MongoDB is empty, so you need to create admin user:

**Option 1: Use Railway MongoDB Shell**
1. Click on MongoDB service
2. Click "Connect"
3. Use MongoDB shell to insert admin user

**Option 2: Use DataInitializer**
Your `DataInitializer.java` will automatically create admin user on first run.

---

## 🧪 Testing Deployment

1. Visit: `https://your-app.railway.app`
2. Should see login page
3. Login with: `admin` / `admin123`
4. Test certificate generation
5. Test verification page

---

## 📊 Monitoring

### View Logs
1. Click on your service
2. Go to "Deployments" tab
3. Click on latest deployment
4. View real-time logs

### Check Metrics
1. Go to "Metrics" tab
2. View CPU, Memory, Network usage

### Database Access
1. Click on MongoDB service
2. Click "Connect"
3. Use connection string with MongoDB Compass

---

## 💰 Pricing

### Free Tier
- $5 credit per month
- Enough for small projects
- Includes MongoDB

### Paid Plans
- $5/month for additional credit
- Pay-as-you-go
- No hidden fees

---

## 🐛 Troubleshooting

### Build Fails

**Problem**: Maven build fails
**Solution**: 
```bash
# Test locally first
./mvnw clean package -DskipTests

# Check logs in Railway
```

### Application Won't Start

**Problem**: Port binding error
**Solution**: Ensure `server.port=${PORT:8080}` in application.yaml

### MongoDB Connection Failed

**Problem**: Can't connect to MongoDB
**Solution**: 
1. Check `MONGODB_URI` variable is set
2. Verify MongoDB service is running
3. Check connection string format

### 404 Errors

**Problem**: Pages not found
**Solution**:
1. Check base URL is correct
2. Verify templates are in `src/main/resources/templates/`
3. Check controller mappings

---

## 🔐 Security Best Practices

### 1. Change Default Admin Password
After first deployment, change admin password in database.

### 2. Use Environment Variables
Never commit sensitive data:
- MongoDB URI
- Email credentials
- API keys

### 3. Enable HTTPS
Railway provides HTTPS by default - always use it.

### 4. Set Up Backups
1. Go to MongoDB service
2. Enable automated backups
3. Schedule regular exports

---

## 📈 Scaling

### Vertical Scaling
1. Go to service settings
2. Increase memory/CPU allocation
3. Redeploy

### Horizontal Scaling
Railway supports multiple instances:
1. Go to service settings
2. Increase replica count
3. Load balancing is automatic

---

## 🔄 Continuous Deployment

Railway automatically deploys when you push to GitHub:

```bash
# Make changes
git add .
git commit -m "Update feature"
git push origin main

# Railway automatically:
# 1. Detects push
# 2. Builds application
# 3. Runs tests
# 4. Deploys new version
```

---

## 📝 Useful Commands

### View Logs
```bash
# Install Railway CLI
npm i -g @railway/cli

# Login
railway login

# Link project
railway link

# View logs
railway logs
```

### Database Backup
```bash
# Export database
railway run mongodump --uri=$MONGODB_URI

# Import database
railway run mongorestore --uri=$MONGODB_URI dump/
```

---

## 🎯 Checklist

Before going live:
- [ ] Code pushed to GitHub
- [ ] Railway project created
- [ ] MongoDB added
- [ ] Environment variables set
- [ ] Application deployed successfully
- [ ] Admin user created
- [ ] Base URL updated
- [ ] Email configured (optional)
- [ ] SSL/HTTPS working
- [ ] Test certificate generation
- [ ] Test verification page
- [ ] Test QR codes
- [ ] Monitor logs for errors

---

## 📞 Support

### Railway Support
- Documentation: https://docs.railway.app
- Discord: https://discord.gg/railway
- Twitter: @Railway

### Application Issues
- Check logs in Railway dashboard
- Review TROUBLESHOOTING.md
- Test locally first

---

## 🎉 Success!

Your certificate system is now live at:
**https://your-app.railway.app**

Share the verification URL:
**https://your-app.railway.app/verify**

---

**Deployment Date**: February 27, 2026  
**Platform**: Railway  
**Status**: Ready for Production
