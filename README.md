# ai-qr-whatsapp-automation
AI-powered QR Code Generator and WhatsApp Automation system using Java, Gemini AI, Evolution API and MySQL.
# AI-Powered QR Code Generator & WhatsApp Automation

## Project Overview

This project is an AI-powered automation system that generates QR codes from URLs and automatically shares them through WhatsApp. The application also uses Gemini AI to generate a brief description of the provided URL and stores all transaction details in a MySQL database.

The main objective of this project is to automate the process of generating, enriching, and distributing QR codes while maintaining a record of all activities.

---

## Problem Statement

Sharing URLs manually can be inconvenient and difficult to track. This project simplifies the process by converting URLs into QR codes, generating meaningful descriptions using AI, and delivering them automatically through WhatsApp.

---

## Features

- Generate QR codes from URLs
- Generate AI-based descriptions using Gemini AI
- Send QR codes through WhatsApp automatically
- Store transaction details in MySQL
- Docker-based deployment support
- Maintain logs for tracking and auditing

---

## Tech Stack

### Backend
- Java
- Maven

### Database
- MySQL

### APIs & Libraries
- Gemini AI API
- Evolution WhatsApp API
- ZXing QR Code Library

### Tools
- Docker
- Git
- IntelliJ IDEA / Eclipse

---

## System Workflow

1. User enters a URL and recipient mobile number.
2. The application generates a QR code for the URL.
3. Gemini AI creates a short description of the content.
4. The QR code and description are sent through WhatsApp.
5. Transaction details are stored in the MySQL database.

---

## Architecture

```text
User Input (URL + Mobile Number)
               ↓
        Java Backend
               ↓
      QR Code Generation
          (ZXing)
               ↓
 AI Description Generation
        (Gemini AI)
               ↓
 WhatsApp Automation
     (Evolution API)
               ↓
       MySQL Database
```

---

## Project Information

- Duration: 2–3 months
- Domain: AI Automation / Backend Development
- Role: Backend Java Developer
- Team Size: 1 (Individual Project)
- Complexity: Medium

---

## Security Note

Sensitive information such as:

- API keys
- Database credentials
- Passwords
- Tokens
- Phone numbers

have been removed from this public repository.

---

## Future Enhancements

- Develop a web-based user interface
- Support bulk QR code generation
- Add analytics and reporting features
- Improve message scheduling capabilities
