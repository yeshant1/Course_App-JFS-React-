package com.cg.spring_boot_microservice_3_api_gateway.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.cg.spring_boot_microservice_3_api_gateway.dto.PaymentSuccessRequest;
import com.cg.spring_boot_microservice_3_api_gateway.request.PurchaseServiceRequest;
import com.cg.spring_boot_microservice_3_api_gateway.security.UserPrincipal;
import com.cg.spring_boot_microservice_3_api_gateway.service.PurchaseGatewayService;


@RestController
@RequestMapping("gateway/purchase")//pre-path
@Tag(name = "Purchase Gateway", description = "Purchase management APIs via Gateway")
public class PurchaseController
{
    @Autowired
    private PurchaseServiceRequest purchaseServiceRequest;
    
    @Autowired
    private PurchaseGatewayService purchaseGatewayService;

    @Operation(summary = "Create payment", description = "Creates a payment order for a given amount via Gateway.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment order created successfully")
    })
    @PostMapping("/create-payment")
    public ResponseEntity<Map<String, String>> createPayment(@Parameter(description = "Payment amount") @RequestParam Double amount) {
        Map<String, String> response = purchaseServiceRequest.createPayment(amount);
        return ResponseEntity.ok(response);
    }



    @Operation(summary = "Confirm payment success", description = "Processes a successful payment and creates a purchase record via Gateway.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase confirmed successfully")
    })
    @PostMapping(value = "/payment-success", consumes = "multipart/form-data")
    public ResponseEntity<?> confirmPaymentSuccess(@Parameter(description = "Payment success request data") @ModelAttribute PaymentSuccessRequest request) {
        return new ResponseEntity<>(purchaseServiceRequest.confirmPaymentSuccess(request), HttpStatus.OK);
    }




    @Operation(summary = "Get all purchases of authorized user", description = "Returns all purchases for the authenticated user via Gateway.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of purchases returned successfully")
    })
    @GetMapping//gateway/purchase
    public ResponseEntity<?> getAllPurchasesOfAuthorizedUser(@Parameter(description = "Authenticated user principal") @AuthenticationPrincipal UserPrincipal userPrincipal)
    {
        return ResponseEntity.ok(purchaseServiceRequest.getAllPurchasesOfUser(userPrincipal.getId()));
    }
}
