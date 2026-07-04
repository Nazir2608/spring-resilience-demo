package com.nazir.resilience.throttling;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymenttController {

    private final PaymentService service;

    @GetMapping("/pay")
    public String pay() {
        return service.processPayment();
    }
}