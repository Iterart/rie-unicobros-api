package net.iterart.unicobros.dto;

public class PaymentIntentResponse {

    private int statusCode;
    private String body;
    private boolean success;
    private String message;

    public PaymentIntentResponse() {
    }

    public PaymentIntentResponse(int statusCode, String body, boolean success, String message) {
        this.statusCode = statusCode;
        this.body = body;
        this.success = success;
        this.message = message;
    }

    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
