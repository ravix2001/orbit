//package com.ravi.orbit.service;
//
//import com.ravi.orbit.entity.PaymentDetails;
//import com.ravi.orbit.enums.PAYMENT_METHOD;
//import com.ravi.orbit.enums.PAYMENT_STATUS;
//import com.ravi.orbit.repository.PaymentRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class PaymentService {
//
//    private final PaymentRepository paymentRepository;
//
//    public PaymentService(PaymentRepository paymentRepository) {
//        this.paymentRepository = paymentRepository;
//    }
//
//    public PaymentDetails initiatePayment(String paymentGateway, double amount, PAYMENT_METHOD paymentMethod){
//        PaymentDetails paymentDetails = new PaymentDetails();
//        paymentDetails.setPaymentId("PMT-" + System.currentTimeMillis());       // Generate unique payment ID
//        paymentDetails.setTransactionId("TRX-" + System.currentTimeMillis());   // Generate unique transaction ID
//        paymentDetails.setAmount(amount);
//        paymentDetails.setPaymentGateway(paymentGateway);
//
//        // TODO: Call payment gateway's API here and update 'status' and 'referenceId'
//        return paymentDetails;
//    }
//
//    public PaymentDetails updatePaymentStatus(String transactionId, PAYMENT_STATUS status) {
//        // TODO: Fetch payment details by transactionId and update status
//        PaymentDetails paymentDetails = paymentRepository.findByTransactionId(transactionId);
//        paymentDetails.setTransactionId(transactionId);
//        paymentDetails.setUpdatedAt(LocalDateTime.now());
//        paymentDetails.setPaymentStatus(status);
//        return paymentDetails;
//    }
//
//    public PAYMENT_STATUS checkPaymentStatus(String transactionId) {
//        // TODO: Call payment gateway's status query API
//        return PAYMENT_STATUS.PROCESSING; // Mock response
//    }
//
//}
