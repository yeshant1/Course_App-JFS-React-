package com.cg.springbootmicroservice2purchase.controller;

import com.cg.springbootmicroservice2purchase.dto.PaymentSuccessRequest;
import com.cg.springbootmicroservice2purchase.model.Purchase;
import com.cg.springbootmicroservice2purchase.service.PaymentService;
import com.cg.springbootmicroservice2purchase.service.PurchaseService;
import com.razorpay.Order;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/purchase")
@Tag(name = "Purchase", description = "Purchase management APIs")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Create payment", description = "Creates a payment order for a given amount.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment order created successfully"),
        @ApiResponse(responseCode = "500", description = "Payment creation failed")
    })
    @PostMapping(value = "/create-payment", produces = "application/json")
    public ResponseEntity<Map<String, String>> createPayment(@Parameter(description = "Payment amount") @RequestParam Double amount) {
        try {
            Order order = paymentService.createOrder(amount);
            Map<String, String> response = new HashMap<>();
            response.put("orderId", order.get("id"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Payment creation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    @Operation(summary = "Confirm payment success", description = "Processes a successful payment and creates a purchase record.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Purchase created successfully")
    })
    @PostMapping(value = "/payment-success", consumes = "multipart/form-data")
    public ResponseEntity<?> paymentSuccess(@Parameter(description = "Payment success request data") @ModelAttribute PaymentSuccessRequest request) {
        Purchase savedPurchase = purchaseService.processSuccessfulPayment(
            request.getUserId(), request.getCourseId(), request.getTitle(), request.getPrice());
        return new ResponseEntity<>(savedPurchase, HttpStatus.CREATED);
    }


    @Operation(summary = "Get all purchases of user", description = "Returns all purchases for a given user ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of purchases returned successfully")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllPurchasesOfUser(@Parameter(description = "User ID") @PathVariable Long userId) {
        return ResponseEntity.ok(purchaseService.findAllPurchasesOfUser(userId));
    }
}
