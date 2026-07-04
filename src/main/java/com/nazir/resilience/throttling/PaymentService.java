package com.nazir.resilience.throttling;

import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;

@Service
public class PaymentService {

    private final Semaphore semaphore = new Semaphore(2);

    public String processPayment() {
        boolean acquired = semaphore.tryAcquire();
        if (!acquired) {
            return "Server Busy";
        }
        try {
            System.out.println(Thread.currentThread().getName() + " processing payment");
            Thread.sleep(10000);
            return "Payment Success";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }
}