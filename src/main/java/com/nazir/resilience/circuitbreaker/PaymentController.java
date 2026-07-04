package com.nazir.resilience.circuitbreaker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping
    public String payment() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("Payment service failed");
        }
        return "Payment Success";
    }
}