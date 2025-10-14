package com.example.sep490_eocims.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eocims/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/get")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello World");
    }
}
