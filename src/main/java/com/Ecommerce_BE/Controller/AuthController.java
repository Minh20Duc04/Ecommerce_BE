package com.Ecommerce_BE.Controller;

import com.Ecommerce_BE.Dto.LoginRequest;
import com.Ecommerce_BE.Dto.Response;
import com.Ecommerce_BE.Dto.UserDto;
import com.Ecommerce_BE.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserDto registrationRequest)
    {
        System.out.println(registrationRequest);
        return ResponseEntity.ok(userService.registerUser(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginRequest)
    {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
















}
