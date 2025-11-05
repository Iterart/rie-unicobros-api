package net.iterart.unicobros.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentIntentRequest {

    private Double total;
    private String currency;
    private String reference;
    private String description;

    @JsonProperty("return_url")
    private String returnUrl;

    private String webhook;

    public PaymentIntentRequest() {
    }

    public PaymentIntentRequest(Double total, String currency, String reference, String description, String returnUrl, String webhook) {
        this.total = total;
        this.currency = currency;
        this.reference = reference;
        this.description = description;
        this.returnUrl = returnUrl;
        this.webhook = webhook;
    }

    // Getters and Setters
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }
}
