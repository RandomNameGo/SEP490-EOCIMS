package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.request.SemesterRequest;
import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.services.SemesterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eocims/api/v1")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @PostMapping("/semester")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addSemester(@RequestBody @Valid SemesterRequest semesterRequest) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data(semesterService.creteSemester(semesterRequest))
                .error(null)
                .build()
        );
    }
}
