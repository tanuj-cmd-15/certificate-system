# Deployment Quick Reference Card

## 🚀 Choose Your Platform

```
┌─────────────────────────────────────────────────────────────┐
│  Need quick deployment?           → Railway (5 min)         │
│  Want free tier?                  → Render (20 min)         │
│  Need enterprise features?        → AWS/Azure (1-2 hours)   │
│  Want to use Vercel?              → ❌ NOT SUPPORTED        │
└─────────────────────────────────────────────────────────────┘
```

---

## 🚂 Railway (Recommended for Beginners)

### Quick Steps
```bash
1. Push to GitHub
2. Go to railway.app
3. Connect repo
4. Add MongoDB
5. Deploy!
```

### Time: ⏱️ 5 minutes
### Cost: 💰 $5 credit/month (free)
### Guide: 📖 DEPLOY_RAILWAY.md

---

## 🎨 Render (Best Free Tier)

### Quick Steps
```bash
1. Create MongoDB Atlas account
2. Get connection string
3. Push to GitHub
4. Go to render.com
5. Connect repo
6. Add environment variables
7. Deploy!
```

### Time: ⏱️ 20 minutes
### Cost: 💰 Free (with cold starts)
### Guide: 📖 DEPLOY_RENDER.md

---

## ⚙️ Environment Variables Needed

### Railway
```
SPRING_PROFILES_ACTIVE=prod
APP_BASE_URL=https://your-app.railway.app
MAIL_ENABLED=false
```

### Render
```
SPRING_DATA_MONGODB_URI=mongodb+srv://...
SPRING_PROFILES_ACTIVE=prod
APP_BASE_URL=https://your-app.onrender.com
MAIL_ENABLED=false
PORT=8080
```

---

## 📋 Pre-Deployment Checklist

- [ ] Code pushed to GitHub
- [ ] MongoDB ready (Railway: auto, Render: Atlas)
- [ ] Environment variables prepared
- [ ] Admin password noted
- [ ] Email credentials ready (optional)
- [ ] Base URL decided

---

## 🧪 Post-Deployment Testing

```bash
# 1. Check if app is running
curl https://your-app-url.com/login

# 2. Login as admin
Username: admin
Password: admin123

# 3. Generate test certificate

# 4. Verify certificate
Visit: https://your-app-url.com/verify

# 5. Test QR code with phone
```

---

## 💰 Cost Comparison

| Platform | Free Tier | Paid (No Cold Start) |
|----------|-----------|---------------------|
| Railway  | $5 credit | $10-20/month        |
| Render   | 750 hrs   | $7/month            |
| Heroku   | ❌ None   | $7/month            |
| AWS      | Limited   | $20-50/month        |

---

## 🐛 Common Issues

### Build Fails
```bash
# Test locally first
./mvnw clean package -DskipTests
```

### MongoDB Connection Failed
```
Check:
1. Connection string format
2. Password special characters
3. IP whitelist (0.0.0.0/0)
4. Database name
```

### Application Won't Start
```
Check:
1. PORT environment variable
2. MongoDB URI
3. Java version (17+)
4. Memory limits
```

---

## 📞 Quick Support Links

### Railway
- Docs: https://docs.railway.app
- Discord: https://discord.gg/railway

### Render
- Docs: https://render.com/docs
- Community: https://community.render.com

### MongoDB Atlas
- Docs: https://docs.atlas.mongodb.com

---

## ✅ Success Indicators

After deployment, you should see:
- ✅ Login page loads
- ✅ Admin can login
- ✅ Certificates can be generated
- ✅ Verification page works
- ✅ QR codes display
- ✅ HTTPS is enabled
- ✅ No errors in logs

---

## 🎯 Next Steps After Deployment

1. **Change admin password**
2. **Configure email** (optional)
3. **Test all features**
4. **Share verification URL**
5. **Set up monitoring**
6. **Configure backups**
7. **Update documentation**

---

## 📱 Share These URLs

After deployment, share:

```
Login: https://your-app-url.com/login
Verify: https://your-app-url.com/verify
Admin: https://your-app-url.com/admin
```

---

## 🔐 Security Reminders

- [ ] Change default admin password
- [ ] Use environment variables for secrets
- [ ] Enable HTTPS (automatic on Railway/Render)
- [ ] Set up database backups
- [ ] Monitor access logs
- [ ] Update dependencies regularly

---

**Ready to deploy?**

👉 **Beginners**: Start with [DEPLOY_RAILWAY.md](DEPLOY_RAILWAY.md)  
👉 **Free tier**: Try [DEPLOY_RENDER.md](DEPLOY_RENDER.md)  
👉 **Compare all**: Read [DEPLOYMENT_OPTIONS.md](DEPLOYMENT_OPTIONS.md)

---

**Last Updated**: February 27, 2026  
**Status**: Ready for Production Deployment
