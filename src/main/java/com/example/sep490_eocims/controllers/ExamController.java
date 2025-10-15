package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.request.ExamCreateRequest;
import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.dto.response.ExamResponse;
import com.example.sep490_eocims.services.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("eocims/api/v1")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping("/exam")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addExam(@RequestBody @Valid ExamCreateRequest examCreateRequest) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data(examService.createExam(examCreateRequest))
                .error(null)
                .build()
        );
    }

    @GetMapping("/exams")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllExams() {
        return ResponseEntity.ok().body(ApiResponse.<List<ExamResponse>>builder()
                .success(true)
                .message("Success")
                .data(examService.getAllExams())
                .error(null)
                .build()
        );
    }
}
