package net.iterart.unicobros.web.controller;

import net.iterart.unicobros.dto.PaymentIntentRequest;
import net.iterart.unicobros.dto.PaymentIntentResponse;
import net.iterart.unicobros.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/unicobros")
public class UnicobrosController {

    private final PaymentService paymentService;

    public UnicobrosController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello, Unicobros!";
    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(@RequestBody PaymentIntentRequest paymentRequest) {
        PaymentIntentResponse response = paymentService.createPaymentIntent(paymentRequest);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }

}
