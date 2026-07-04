package com.nazir.resilience.circuitbreaker;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderrController {

    private final PaymentClient paymentClient;

    @GetMapping("/order")
    public String order() {
        return paymentClient.callPayment();
    }
}