package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.request.ExamSubjectBatchRequest;
import com.example.sep490_eocims.dto.request.ExamSubjectRequest;
import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.dto.response.ExamSubjectResponse;
import com.example.sep490_eocims.services.ExamSubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("eocims/api/v1")
@RequiredArgsConstructor
public class ExamSubjectController {

    private final ExamSubjectService examSubjectService;

    @PostMapping("/exam-subject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addExamSubject(@RequestBody @Valid ExamSubjectRequest examSubjectRequest) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data(examSubjectService.createExamSubject(examSubjectRequest))
                .error(null)
                .build()
        );
    }

    @PostMapping("/exam-subjects/batch")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMultipleExamSubjects(@RequestBody @Valid ExamSubjectBatchRequest examSubjectBatchRequest) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data(examSubjectService.createMultipleExamSubjects(examSubjectBatchRequest))
                .error(null)
                .build()
        );
    }

    @GetMapping("/exam-subjects")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllExamSubjects() {
        return ResponseEntity.ok().body(ApiResponse.<List<ExamSubjectResponse>>builder()
                .success(true)
                .message("Success")
                .data(examSubjectService.getAllExamSubjects())
                .error(null)
                .build()
        );
    }

    @GetMapping("/exam-subjects/exam/{examId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getExamSubjectsByExamId(@PathVariable Long examId) {
        return ResponseEntity.ok().body(ApiResponse.<List<ExamSubjectResponse>>builder()
                .success(true)
                .message("Success")
                .data(examSubjectService.getExamSubjectsByExamId(examId))
                .error(null)
                .build()
        );
    }
}