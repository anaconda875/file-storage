package com.example.filestorage.controller;

import com.example.filestorage.dto.request.RegisterRequest;
import com.example.filestorage.dto.response.LoginResponse;
import com.example.filestorage.dto.response.RegisterResponse;
import com.example.filestorage.service.LoginService;
import com.example.filestorage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final LoginService loginService;

    @PostMapping(consumes = {"multipart/form-data"},path = "/register")
    public RegisterResponse register(@Valid @RequestPart RegisterRequest registerRequest, @RequestPart byte[] key) {
        registerRequest.setKey(key);

        return userService.register(registerRequest);
    }

    @PostMapping(consumes = {"multipart/form-data"},path = "/login")
    public LoginResponse login(@RequestPart byte[] key) {
        return loginService.login(key);
    }

}
