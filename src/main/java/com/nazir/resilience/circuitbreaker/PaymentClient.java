package com.nazir.resilience.circuitbreaker;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentClient {

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "paymentService", fallbackMethod = "fallback")
    public String callPayment() {
        System.out.println("Calling payment service");
        return restTemplate.getForObject("http://localhost:8080/payment", String.class);
    }

    public String fallback(Exception ex) {
        System.out.println("FALLBACK EXECUTED");
        System.out.println("Exception: " + ex.getClass());
        return "Fallback Response";
    }
}