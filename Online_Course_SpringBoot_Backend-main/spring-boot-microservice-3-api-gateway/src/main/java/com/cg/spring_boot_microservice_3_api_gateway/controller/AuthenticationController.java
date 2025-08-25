//package com.cg.spring_boot_microservice_3_api_gateway.controller;
//
//import com.cg.spring_boot_microservice_3_api_gateway.model.User;
//import com.cg.spring_boot_microservice_3_api_gateway.service.AuthenticationService;
//import com.cg.spring_boot_microservice_3_api_gateway.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("api/authentication")
//public class AuthenticationController
//{
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("sign-up")//api/authentication/sign-up
//    public ResponseEntity<?> signUp(@RequestBody User user)
//    {
//        if (userService.findByUsername(user.getUsername()).isPresent())
//        {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
//    }
//
//    @PostMapping("sign-in")//api/authentication/sign-in
//    public ResponseEntity<?> signIn(@RequestBody User user)
//    {
//        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
//    }
//}



package com.cg.spring_boot_microservice_3_api_gateway.controller;

import com.cg.spring_boot_microservice_3_api_gateway.exception.UserAlreadyExistsException;
import com.cg.spring_boot_microservice_3_api_gateway.model.User;
import com.cg.spring_boot_microservice_3_api_gateway.service.AuthenticationService;
import com.cg.spring_boot_microservice_3_api_gateway.service.UserService;
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
@RequestMapping("api/authentication")
@Tag(name = "Authentication", description = "User authentication APIs")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Sign up", description = "Registers a new user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "409", description = "Username already taken")
    })
    @PostMapping("sign-up") // api/authentication/sign-up
    public ResponseEntity<?> signUp(@Parameter(description = "User object") @RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username '" + user.getUsername() + "' is already taken.");
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Sign in", description = "Authenticates a user and returns a JWT token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User authenticated successfully")
    })
    @PostMapping("sign-in") // api/authentication/sign-in
    public ResponseEntity<?> signIn(@Parameter(description = "User object") @RequestBody User user) {
        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
    }
}
