@echo off
echo ========================================
echo Email Configuration Setup
echo SPPU Department of Technology
echo ========================================
echo.
echo This script will help you configure email for the certificate system.
echo.
echo OPTION 1: Skip Email Configuration (Recommended for Testing)
echo - Certificates will still be generated
echo - Email status will show "Failed"
echo - All other features work normally
echo.
echo OPTION 2: Configure Gmail
echo - You need a Gmail account
echo - 2-Factor Authentication must be enabled
echo - You need to generate an App Password
echo.
set /p choice="Enter your choice (1 or 2): "

if "%choice%"=="1" (
    echo.
    echo ✓ Email disabled. Application will work without email.
    echo ✓ Certificates will be generated normally.
    echo ✓ You can enable email later by following EMAIL_SETUP.md
    echo.
    echo Press any key to continue...
    pause >nul
    exit /b 0
)

if "%choice%"=="2" (
    echo.
    echo Gmail Configuration Steps:
    echo.
    echo 1. Go to: https://myaccount.google.com/security
    echo 2. Enable 2-Step Verification
    echo 3. Go to: https://myaccount.google.com/apppasswords
    echo 4. Generate App Password for "Mail" and "Windows Computer"
    echo 5. Copy the 16-character password
    echo.
    echo Press any key when you have your App Password ready...
    pause >nul
    echo.
    set /p email="Enter your Gmail address: "
    set /p password="Enter your App Password (16 characters): "
    
    echo.
    echo Creating environment variables...
    setx MAIL_USERNAME "%email%"
    setx MAIL_PASSWORD "%password%"
    setx MAIL_ENABLED "true"
    
    echo.
    echo ✓ Email configured successfully!
    echo ✓ Environment variables set:
    echo   - MAIL_USERNAME=%email%
    echo   - MAIL_PASSWORD=****** (hidden)
    echo   - MAIL_ENABLED=true
    echo.
    echo ⚠️  IMPORTANT: You must restart the application for changes to take effect!
    echo.
    echo Press any key to continue...
    pause >nul
    exit /b 0
)

echo.
echo Invalid choice. Please run the script again.
pause
