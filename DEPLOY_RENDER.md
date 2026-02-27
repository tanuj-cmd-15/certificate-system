# Deploy to Render - Complete Guide

## 🎨 Why Render?

Render is a great alternative for deploying Spring Boot applications:
- ✅ Free tier available
- ✅ Automatic HTTPS
- ✅ Git-based deployment
- ✅ Easy MongoDB Atlas integration
- ✅ Zero-downtime deploys
- ✅ Auto-scaling

---

## 📋 Prerequisites

1. GitHub account
2. Render account (sign up at https://render.com)
3. MongoDB Atlas account (free at https://www.mongodb.com/cloud/atlas)
4. Your code pushed to GitHub

---

## 🚀 Step-by-Step Deployment

### Part A: Set Up MongoDB Atlas

#### Step 1: Create MongoDB Atlas Account
1. Go to https://www.mongodb.com/cloud/atlas
2. Sign up for free account
3. Create a new cluster (M0 Free tier)
4. Choose cloud provider and region (closest to you)
5. Wait for cluster creation (2-5 minutes)

#### Step 2: Configure Database Access
1. Go to "Database Access" in left menu
2. Click "Add New Database User"
3. Create user:
   - Username: `certificate_admin`
   - Password: Generate secure password (save it!)
   - Database User Privileges: "Read and write to any database"
4. Click "Add User"

#### Step 3: Configure Network Access
1. Go to "Network Access" in left menu
2. Click "Add IP Address"
3. Click "Allow Access from Anywhere" (0.0.0.0/0)
4. Click "Confirm"

#### Step 4: Get Connection String
1. Go to "Database" in left menu
2. Click "Connect" on your cluster
3. Choose "Connect your application"
4. Copy connection string:
   ```
   mongodb+srv://certificate_admin:<password>@cluster0.xxxxx.mongodb.net/certificate_db?retryWrites=true&w=majority
   ```
5. Replace `<password>` with your actual password
6. Save this connection string!

---

### Part B: Deploy to Render

#### Step 1: Push Code to GitHub

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

#### Step 2: Sign Up for Render

1. Go to https://render.com
2. Click "Get Started"
3. Sign up with GitHub
4. Authorize Render

#### Step 3: Create Web Service

1. Click "New +" → "Web Service"
2. Connect your GitHub repository
3. Configure service:
   - **Name**: `sppu-certificate-system`
   - **Region**: Choose closest to you
   - **Branch**: `main`
   - **Runtime**: `Docker` or `Java`
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/certificate-system-0.0.1-SNAPSHOT.jar`

#### Step 4: Configure Environment Variables

In the "Environment" section, add:

```
SPRING_DATA_MONGODB_URI=mongodb+srv://certificate_admin:YOUR_PASSWORD@cluster0.xxxxx.mongodb.net/certificate_db?retryWrites=true&w=majority
SPRING_PROFILES_ACTIVE=prod
APP_BASE_URL=https://sppu-certificate-system.onrender.com
MAIL_ENABLED=false
PORT=8080
```

#### Step 5: Choose Plan

1. Select "Free" plan
2. Click "Create Web Service"
3. Render will start building your app

#### Step 6: Wait for Deployment

- First build takes 10-15 minutes
- Watch logs in real-time
- Status will change to "Live" when ready

---

## 🔧 Configuration Files

### Update application.yaml

Ensure your `application.yaml` supports environment variables:

```yaml
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:default}
  data:
    mongodb:
      uri: ${SPRING_DATA_MONGODB_URI:mongodb://localhost:27017/certificate_db}
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
```

### Create render.yaml (Optional)

Create `render.yaml` in root for infrastructure as code:

```yaml
services:
  - type: web
    name: sppu-certificate-system
    env: java
    buildCommand: ./mvnw clean package -DskipTests
    startCommand: java -jar target/certificate-system-0.0.1-SNAPSHOT.jar
    envVars:
      - key: SPRING_PROFILES_ACTIVE
        value: prod
      - key: SPRING_DATA_MONGODB_URI
        sync: false
      - key: APP_BASE_URL
        value: https://sppu-certificate-system.onrender.com
      - key: MAIL_ENABLED
        value: false
```

---

## 🧪 Testing Deployment

1. Visit your Render URL: `https://sppu-certificate-system.onrender.com`
2. Should see login page
3. Login with: `admin` / `admin123`
4. Test certificate generation
5. Test verification page

---

## 📊 Monitoring

### View Logs
1. Go to your service dashboard
2. Click "Logs" tab
3. View real-time logs
4. Filter by severity

### Check Metrics
1. Go to "Metrics" tab
2. View:
   - CPU usage
   - Memory usage
   - Request count
   - Response times

### Set Up Alerts
1. Go to "Settings"
2. Configure email alerts for:
   - Deploy failures
   - High CPU usage
   - Service downtime

---

## 💰 Pricing

### Free Tier
- 750 hours/month (enough for 1 service)
- Automatic sleep after 15 min inactivity
- Wakes up on request (cold start ~30 seconds)
- 512 MB RAM
- Shared CPU

### Paid Plans
- **Starter**: $7/month
  - No sleep
  - 512 MB RAM
  - Dedicated resources
- **Standard**: $25/month
  - 2 GB RAM
  - Better performance

---

## 🐛 Troubleshooting

### Build Fails

**Problem**: Maven build error
**Solution**:
```bash
# Test locally
./mvnw clean package -DskipTests

# Check Java version in logs
# Render uses Java 17 by default
```

### Service Won't Start

**Problem**: Application crashes on startup
**Solution**:
1. Check logs for errors
2. Verify MongoDB connection string
3. Ensure PORT environment variable is set
4. Check memory limits

### MongoDB Connection Failed

**Problem**: Can't connect to Atlas
**Solution**:
1. Verify connection string is correct
2. Check password has no special characters (URL encode if needed)
3. Verify IP whitelist includes 0.0.0.0/0
4. Test connection locally first

### Cold Starts (Free Tier)

**Problem**: Service sleeps after 15 minutes
**Solution**:
1. Upgrade to paid plan ($7/month)
2. OR use a ping service (UptimeRobot) to keep it awake
3. OR accept cold starts (30 second delay)

---

## 🔐 Security Best Practices

### 1. Secure MongoDB
- Use strong passwords
- Enable IP whitelist
- Use connection string encryption
- Regular backups

### 2. Environment Variables
- Never commit secrets
- Use Render's secret management
- Rotate credentials regularly

### 3. HTTPS
- Render provides free SSL
- Always use HTTPS URLs
- Update base URL in config

### 4. Database Backups
MongoDB Atlas provides:
- Continuous backups (paid)
- Manual snapshots (free)
- Point-in-time recovery

---

## 📈 Performance Optimization

### 1. Reduce Cold Starts
```yaml
# Add health check endpoint
@RestController
public class HealthController {
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
```

### 2. Enable Caching
```yaml
spring:
  cache:
    type: simple
```

### 3. Optimize Build
```xml
<!-- In pom.xml -->
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <layers>
            <enabled>true</enabled>
        </layers>
    </configuration>
</plugin>
```

---

## 🔄 Continuous Deployment

Render automatically deploys on Git push:

```bash
# Make changes
git add .
git commit -m "Update feature"
git push origin main

# Render automatically:
# 1. Detects push
# 2. Builds application
# 3. Runs health checks
# 4. Deploys with zero downtime
```

---

## 🎯 Production Checklist

Before going live:
- [ ] Code pushed to GitHub
- [ ] MongoDB Atlas cluster created
- [ ] Database user created
- [ ] Network access configured
- [ ] Render service created
- [ ] Environment variables set
- [ ] Application deployed successfully
- [ ] Admin user created
- [ ] Base URL updated
- [ ] Email configured (optional)
- [ ] SSL/HTTPS working
- [ ] Health checks passing
- [ ] Test all features
- [ ] Set up monitoring
- [ ] Configure backups

---

## 🆚 Render vs Railway

| Feature | Render | Railway |
|---------|--------|---------|
| Free Tier | 750 hrs/month | $5 credit/month |
| Cold Starts | Yes (15 min) | No |
| MongoDB | External (Atlas) | Built-in |
| Build Time | 10-15 min | 5-10 min |
| Ease of Use | Medium | Easy |
| Scaling | Manual | Automatic |
| Best For | Production | Development |

---

## 📞 Support

### Render Support
- Documentation: https://render.com/docs
- Community: https://community.render.com
- Status: https://status.render.com

### MongoDB Atlas Support
- Documentation: https://docs.atlas.mongodb.com
- Support: https://support.mongodb.com

---

## 🎉 Success!

Your certificate system is now live at:
**https://sppu-certificate-system.onrender.com**

Share the verification URL:
**https://sppu-certificate-system.onrender.com/verify**

---

**Deployment Date**: February 27, 2026  
**Platform**: Render + MongoDB Atlas  
**Status**: Ready for Production
