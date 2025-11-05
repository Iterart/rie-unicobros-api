# Unicobros Payment API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)

A Spring Boot REST API for integrating with the Unicobros payment gateway. This service provides endpoints for creating payment intents and managing payment transactions.

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Running the Application](#-running-the-application)
- [API Endpoints](#-api-endpoints)
- [Project Structure](#-project-structure)
- [Usage Examples](#-usage-examples)
- [Logging](#-logging)
- [Contributing](#-contributing)

## ✨ Features

- 🔐 Secure payment intent creation via Unicobros API
- 🚀 HTTP/2 support for optimal performance
- 📝 Comprehensive logging for debugging and monitoring
- 🏗️ Clean architecture with separation of concerns
- 🔧 Externalized configuration
- ✅ Input validation and error handling
- 📊 RESTful API design
- 🔄 Hot reload during development

## 🛠 Tech Stack

- **Framework:** Spring Boot 3.5.7
- **Language:** Java 17
- **Build Tool:** Maven
- **Database:** MySQL 8
- **ORM:** Hibernate/JPA
- **HTTP Client:** Java HttpClient (HTTP/2)
- **JSON Processing:** Jackson
- **Development:** Spring Boot DevTools
- **Logging:** SLF4J with Logback

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- ☕ **Java 17** or higher
- 📦 **Maven 3.6+**
- 🗄️ **MySQL 8.0+**
- 🔑 **Unicobros API credentials** (API Key and Access Token)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd unicobros
   ```

2. **Set up MySQL database**
   ```sql
   CREATE DATABASE unicobros_db;
   CREATE USER 'unicobros_user'@'localhost' IDENTIFIED BY 'securepassword';
   GRANT ALL PRIVILEGES ON unicobros_db.* TO 'unicobros_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Install dependencies**
   ```bash
   mvn clean install
   ```

## ⚙️ Configuration

### Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Server Configuration
spring.application.name=unicobros
server.port=8090

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/unicobros_db
spring.datasource.username=unicobros_user
spring.datasource.password=securepassword

# Unicobros API Configuration
unicobros.api.key=YOUR_API_KEY_HERE
unicobros.api.access-token=YOUR_ACCESS_TOKEN_HERE
unicobros.api.url=https://api.unicobros.com.ar
```

### Environment Variables (Optional)

You can also set configuration via environment variables:

```bash
export UNICOBROS_API_KEY=your-api-key
export UNICOBROS_API_ACCESS_TOKEN=your-access-token
```

## 🏃 Running the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Using Maven Wrapper (Windows)

```powershell
.\mvnw.cmd spring-boot:run
```

### Using Maven Wrapper (Linux/Mac)

```bash
./mvnw spring-boot:run
```

### Using JAR

```bash
mvn clean package
java -jar target/unicobros-0.0.1-SNAPSHOT.jar
```

The application will start on **http://localhost:8090**

## 🌐 API Endpoints

### Health Check

```http
GET /api/unicobros/hello
```

**Response:**
```
Hello, Unicobros!
```

### Create Payment Intent

```http
POST /api/unicobros/payment
Content-Type: application/json
```

**Request Body:**
```json
{
  "total": 100.2,
  "currency": "ars",
  "reference": "ORDER-12345",
  "description": "Descripción de la Venta",
  "return_url": "https://your-site.com/payment/return",
  "webhook": "https://your-site.com/payment/webhook"
}
```

**Success Response (200 OK):**
```json
{
  "statusCode": 200,
  "body": "{\"id\":\"payment-id\",\"url\":\"https://...\"}",
  "success": true,
  "message": "Payment intent created successfully"
}
```

**Error Response (4xx/5xx):**
```json
{
  "statusCode": 500,
  "body": null,
  "success": false,
  "message": "Error: Connection timeout"
}
```

## 📁 Project Structure

```
unicobros/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── net/iterart/unicobros/
│   │   │       ├── UnicobrosApplication.java
│   │   │       ├── config/
│   │   │       │   └── UnicobrosConfig.java
│   │   │       ├── dto/
│   │   │       │   ├── PaymentIntentRequest.java
│   │   │       │   └── PaymentIntentResponse.java
│   │   │       ├── service/
│   │   │       │   └── PaymentService.java
│   │   │       └── web/
│   │   │           └── controller/
│   │   │               └── UnicobrosController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── net/iterart/unicobros/
│               └── UnicobrosApplicationTests.java
├── docs/
│   └── unicobros-api.mmd
├── logs/
│   └── unicobros.log
├── pom.xml
├── README.md
└── PAYMENT_IMPLEMENTATION.md
```

### Architecture Components

- **Controller Layer** (`web.controller`): Handles HTTP requests and responses
- **Service Layer** (`service`): Contains business logic and external API integration
- **Configuration Layer** (`config`): Manages application configuration
- **DTO Layer** (`dto`): Data Transfer Objects for request/response

## 💡 Usage Examples

### cURL

```bash
curl -X POST http://localhost:8090/api/unicobros/payment \
  -H "Content-Type: application/json" \
  -d '{
    "total": 100.2,
    "currency": "ars",
    "reference": "ORDER-12345",
    "description": "Payment for Order #12345",
    "return_url": "https://mystore.com/payment/return",
    "webhook": "https://mystore.com/payment/webhook"
  }'
```

### PowerShell

```powershell
$body = @{
    total = 100.2
    currency = "ars"
    reference = "ORDER-12345"
    description = "Payment for Order #12345"
    return_url = "https://mystore.com/payment/return"
    webhook = "https://mystore.com/payment/webhook"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8090/api/unicobros/payment" `
    -Method Post `
    -Body $body `
    -ContentType "application/json"
```

### JavaScript (Fetch API)

```javascript
const response = await fetch('http://localhost:8090/api/unicobros/payment', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    total: 100.2,
    currency: 'ars',
    reference: 'ORDER-12345',
    description: 'Payment for Order #12345',
    return_url: 'https://mystore.com/payment/return',
    webhook: 'https://mystore.com/payment/webhook'
  })
});

const data = await response.json();
console.log(data);
```

### Python

```python
import requests

payload = {
    "total": 100.2,
    "currency": "ars",
    "reference": "ORDER-12345",
    "description": "Payment for Order #12345",
    "return_url": "https://mystore.com/payment/return",
    "webhook": "https://mystore.com/payment/webhook"
}

response = requests.post(
    "http://localhost:8090/api/unicobros/payment",
    json=payload
)

print(response.json())
```

## 📝 Logging

Logs are configured at multiple levels:

- **Console Output**: INFO level for Spring, DEBUG level for application
- **File Output**: `logs/unicobros.log` with detailed debugging information

### Log Format

```
2025-11-05 10:30:45 [http-nio-8090-exec-1] DEBUG n.i.u.service.PaymentService - Creating payment intent with data: {...}
2025-11-05 10:30:46 [http-nio-8090-exec-1] INFO  n.i.u.service.PaymentService - Payment intent response - Status: 200, Body: {...}
```

### Viewing Logs

**Real-time log monitoring:**
```bash
tail -f logs/unicobros.log
```

**PowerShell:**
```powershell
Get-Content logs/unicobros.log -Wait
```

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

## 📚 Additional Documentation

- [Payment Implementation Guide](PAYMENT_IMPLEMENTATION.md) - Detailed payment integration documentation
- [API Class Diagram](docs/unicobros-api.mmd) - Visual representation of the architecture

## 🔒 Security Notes

⚠️ **Important Security Considerations:**

1. Never commit API keys to version control
2. Use environment variables or secret management tools in production
3. Implement rate limiting for production deployments
4. Use HTTPS in production environments
5. Implement proper authentication/authorization for your endpoints
6. Validate and sanitize all input data

## 🐛 Troubleshooting

### Common Issues

**Issue:** Application won't start - "Port 8090 already in use"
```bash
# Windows
netstat -ano | findstr :8090

# Linux/Mac
lsof -i :8090
```

**Issue:** Database connection error
- Verify MySQL is running
- Check database credentials in `application.properties`
- Ensure database exists and user has proper permissions

**Issue:** Payment API returns 401/403
- Verify API credentials are correct
- Check that credentials are properly loaded from configuration

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **IterArt** - *Initial work*

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Unicobros for the payment gateway API
- All contributors who participate in this project

---

**Made with ☕ and ❤️ using Spring Boot**
