//package com.ravi.orbit.controller;
//
//import com.ravi.orbit.entity.PaymentDetails;
//import com.ravi.orbit.enums.PAYMENT_METHOD;
//import com.ravi.orbit.enums.PAYMENT_STATUS;
//import com.ravi.orbit.service.PaymentService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/payment")
//public class PaymentController {
//
//    private final PaymentService paymentService;
//
//    public PaymentController(PaymentService paymentService) {
//        this.paymentService = paymentService;
//    }
//
//    @PostMapping("/initiate")
//    public ResponseEntity<PaymentDetails> initiatePayment(@RequestParam String paymentGateway, @RequestParam double amount, @RequestParam PAYMENT_METHOD paymentMethod){
//        PaymentDetails paymentDetails = paymentService.initiatePayment(paymentGateway, amount, paymentMethod);
//        return ResponseEntity.ok(paymentDetails);
//    }
//
//    @PostMapping("/updateStatus")
//    public ResponseEntity<PaymentDetails> updatePaymentStatus(
//            @RequestParam String transactionId,
//            @RequestParam PAYMENT_STATUS status
//    ) {
//        PaymentDetails paymentDetails = paymentService.updatePaymentStatus(transactionId, status);
//        return ResponseEntity.ok(paymentDetails);
//    }
//
//    @GetMapping("/status/{transactionId}")
//    public ResponseEntity<PAYMENT_STATUS> getPaymentStatus(@PathVariable String transactionId) {
//        PAYMENT_STATUS status = paymentService.checkPaymentStatus(transactionId);
//        return ResponseEntity.ok(status);
//    }
//
//    // Webhook handler for gateways
//    @PostMapping("/callback")
//    public ResponseEntity<String> paymentCallback(@RequestBody Object callbackRequest) {
//        // Parse the request, extract data, update payment status
//        // TODO: Implement gateway-specific callback logic
//        return ResponseEntity.ok("Callback received");
//    }
//
//
//}
