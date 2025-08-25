package com.cg.spring_boot_microservice_5_notification.controller;

import com.cg.spring_boot_microservice_5_notification.service.UnsafeEmailServiceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/notification")
@Tag(name = "Notification", description = "Notification (Email) management APIs")
public class EmailController {

    private final UnsafeEmailServiceImpl emailService;

    public EmailController(UnsafeEmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "Send email", description = "Sends an email to the specified recipient.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email sent successfully")
    })
    @PostMapping("/send-email")
    public ResponseEntity<Map<String, String>> sendEmail(
            @Parameter(description = "Recipient email address") @RequestParam String to,
            @Parameter(description = "Email subject") @RequestParam String subject,
            @Parameter(description = "Email body") @RequestParam String body) {
        emailService.sendEmail(to, subject, body);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Email sent successfully");
        return ResponseEntity.ok(response);
    }
}


//package com.cg.spring_boot_microservice_5_notification.controller;
//
//import com.cg.spring_boot_microservice_5_notification.dto.EmailRequest;
//import com.cg.spring_boot_microservice_5_notification.service.EmailService;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/notification")
//@RequiredArgsConstructor
//public class EmailController {
//
//    private final EmailService emailService;
//
//    @PostMapping("/send-email")
//    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody EmailRequest request) {
//        emailService.sendEmail(request);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Email sent successfully");
//        return ResponseEntity.ok(response);
//    }
//}
