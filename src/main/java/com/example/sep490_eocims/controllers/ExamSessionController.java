package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.request.ExamSessionCreateRequest;
import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.dto.response.ExamSessionResponse;
import com.example.sep490_eocims.services.ExamSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("eocims/api/v1")
@RequiredArgsConstructor
public class ExamSessionController {

    private final ExamSessionService examSessionService;

    @PostMapping("/exam-session")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addExamSession(@RequestBody @Valid ExamSessionCreateRequest examSessionCreateRequest) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data(examSessionService.createExamSession(examSessionCreateRequest))
                .error(null)
                .build()
        );
    }

    @GetMapping("/exam-sessions")
    @PreAuthorize("hasAnyRole('ADMIN','EXAM_MANAGER')")
    public ResponseEntity<?> getAllExamSessions() {
        return ResponseEntity.ok().body(ApiResponse.<List<ExamSessionResponse>>builder()
                .success(true)
                .message("Success")
                .data(examSessionService.getAllExamSessions())
                .error(null)
                .build()
        );
    }
}