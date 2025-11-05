# Payment Implementation Guide

## Overview
This implementation provides a payment intent creation endpoint using the Unicobros payment API.

## Configuration

### 1. Update API Credentials
Edit `src/main/resources/application.properties` and replace the placeholder values with your actual API credentials:

```properties
unicobros.api.key=your-actual-api-key
unicobros.api.access-token=your-actual-access-token
unicobros.api.url=https://api.unicobros.com.ar
```

## Usage

### Endpoint
- **URL**: `POST http://localhost:8090/api/unicobros/payment/intent`
- **Content-Type**: `application/json`

### Request Body Example
```json
{
  "total": 100.2,
  "currency": "ars",
  "reference": "2982-2XtPXlgSaWccqUyobuv4sEmLYMV0N6oX6MoridMw",
  "description": "Descripción de la Venta",
  "return_url": "https://mobbex.com/sale/return?session=56789",
  "webhook": "https://mobbex.com/sale/webhook?user=1234"
}
```

### Response Example (Success)
```json
{
  "statusCode": 200,
  "body": "{ ... API response ... }",
  "success": true,
  "message": "Payment intent created successfully"
}
```

### Response Example (Error)
```json
{
  "statusCode": 500,
  "body": null,
  "success": false,
  "message": "Error: Connection refused"
}
```

## Testing with cURL

```bash
curl -X POST http://localhost:8090/api/unicobros/payment/intent \
  -H "Content-Type: application/json" \
  -d '{
    "total": 100.2,
    "currency": "ars",
    "reference": "2982-2XtPXlgSaWccqUyobuv4sEmLYMV0N6oX6MoridMw",
    "description": "Descripción de la Venta",
    "return_url": "https://mobbex.com/sale/return?session=56789",
    "webhook": "https://mobbex.com/sale/webhook?user=1234"
  }'
```

## Testing with PowerShell

```powershell
$body = @{
    total = 100.2
    currency = "ars"
    reference = "2982-2XtPXlgSaWccqUyobuv4sEmLYMV0N6oX6MoridMw"
    description = "Descripción de la Venta"
    return_url = "https://mobbex.com/sale/return?session=56789"
    webhook = "https://mobbex.com/sale/webhook?user=1234"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8090/api/unicobros/payment/intent" -Method Post -Body $body -ContentType "application/json"
```

## Architecture

### Components Created

1. **UnicobrosConfig** (`config/UnicobrosConfig.java`)
   - Configuration class that reads API credentials from application.properties
   - Provides centralized access to API settings

2. **PaymentIntentRequest** (`dto/PaymentIntentRequest.java`)
   - DTO for the payment request data
   - Includes all required fields for the Unicobros API

3. **PaymentIntentResponse** (`dto/PaymentIntentResponse.java`)
   - DTO for the payment response
   - Includes status code, body, success flag, and message

4. **PaymentService** (`service/PaymentService.java`)
   - Service class that handles the HTTP communication with Unicobros API
   - Uses Java 11+ HttpClient with HTTP/2
   - Handles JSON serialization/deserialization
   - Includes error handling and logging

5. **UnicobrosController** (`web/controller/UnicobrosController.java`)
   - REST controller with the payment endpoint
   - Delegates business logic to PaymentService
   - Returns appropriate HTTP status codes

## Logging

The application logs payment operations. Check the logs at:
- Console output
- `logs/unicobros.log`

## Error Handling

The implementation includes comprehensive error handling:
- Network errors
- API errors (non-2xx status codes)
- JSON serialization errors
- All errors are logged and returned in a structured format

## Architecture of Unicobros Payment project (Mermaid format)
classDiagram
    class UnicobrosApplication {
        <<SpringBootApplication>>
        +main(String[] args) void
    }
    
    class UnicobrosController {
        <<RestController>>
        -PaymentService paymentService
        +UnicobrosController(PaymentService)
        +hello() String
        +createPaymentIntent(PaymentIntentRequest) ResponseEntity~PaymentIntentResponse~
    }
    
    class PaymentService {
        <<Service>>
        -Logger logger
        -UnicobrosConfig config
        -HttpClient httpClient
        -ObjectMapper objectMapper
        +PaymentService(UnicobrosConfig)
        +createPaymentIntent(PaymentIntentRequest) PaymentIntentResponse
    }
    
    class UnicobrosConfig {
        <<Configuration>>
        -String apiKey
        -String accessToken
        -String apiUrl
        +getApiKey() String
        +getAccessToken() String
        +getApiUrl() String
    }
    
    class PaymentIntentRequest {
        <<DTO>>
        -Double total
        -String currency
        -String reference
        -String description
        -String returnUrl
        -String webhook
        +PaymentIntentRequest()
        +PaymentIntentRequest(Double, String, String, String, String, String)
        +getTotal() Double
        +setTotal(Double) void
        +getCurrency() String
        +setCurrency(String) void
        +getReference() String
        +setReference(String) void
        +getDescription() String
        +setDescription(String) void
        +getReturnUrl() String
        +setReturnUrl(String) void
        +getWebhook() String
        +setWebhook(String) void
    }
    
    class PaymentIntentResponse {
        <<DTO>>
        -int statusCode
        -String body
        -boolean success
        -String message
        +PaymentIntentResponse()
        +PaymentIntentResponse(int, String, boolean, String)
        +getStatusCode() int
        +setStatusCode(int) void
        +getBody() String
        +setBody(String) void
        +isSuccess() boolean
        +setSuccess(boolean) void
        +getMessage() String
        +setMessage(String) void
    }
    
    class HttpClient {
        <<Java HTTP Client>>
    }
    
    class ObjectMapper {
        <<Jackson>>
    }
    
    %% Relationships
    UnicobrosApplication ..> UnicobrosController : starts
    UnicobrosController --> PaymentService : uses
    UnicobrosController ..> PaymentIntentRequest : receives
    UnicobrosController ..> PaymentIntentResponse : returns
    PaymentService --> UnicobrosConfig : uses
    PaymentService --> HttpClient : uses
    PaymentService --> ObjectMapper : uses
    PaymentService ..> PaymentIntentRequest : processes
    PaymentService ..> PaymentIntentResponse : creates
    
    %% Notes
    note for UnicobrosController "REST endpoint at\n/api/unicobros/payment"
    note for PaymentService "Handles HTTP/2 requests\nto Unicobros API"
    note for UnicobrosConfig "Configuration from\napplication.properties"

## Architecture Overview
## The diagram shows a clean layered architecture with:

 # Presentation Layer
 - UnicobrosController: REST endpoint that receives HTTP requests

 # Business Logic Layer
 - PaymentService: Handles payment intent creation logic and API communication

 # Configuration Layer
 - UnicobrosConfig: Manages API credentials and settings

 # Data Transfer Layer
 - PaymentIntentRequest: Input data structure
 - PaymentIntentResponse: Output data structure

 # External Dependencies
 - HttpClient: Java 11+ HTTP/2 client
 - ObjectMapper: Jackson JSON serialization

🔗 Key Relationships:

1) Controller → Service: Dependency injection of PaymentService
2) Service → Config: Uses configuration for API credentials
3) Service → DTOs: Processes request and creates response
4) Service → HttpClient: Sends HTTP/2 requests to external API
5) Service → ObjectMapper: Serializes/deserializes JSON

** This follows SOLID principles with proper separation of concerns and dependency injection!
