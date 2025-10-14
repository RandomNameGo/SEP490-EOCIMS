package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.dto.response.LoginResponse;
import com.example.sep490_eocims.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eocims/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("email") String email) {
        return ResponseEntity.ok().body(ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Success")
                .data(authService.login(email))
                .build()
        );
    }
}
