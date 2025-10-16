package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.request.SubjectRequest;
import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.dto.response.SubjectResponse;
import com.example.sep490_eocims.services.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("eocims/api/v1")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/subject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addSubject(@RequestBody @Valid SubjectRequest subjectRequest) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data(subjectService.createSubject(subjectRequest))
                .error(null)
                .build()
        );
    }

    @GetMapping("/subjects")
    @PreAuthorize("hasAnyRole('ADMIN','EXAM_MANAGER')")
    public ResponseEntity<?> getAllSubjects() {
        return ResponseEntity.ok().body(ApiResponse.<List<SubjectResponse>>builder()
                .success(true)
                .message("Success")
                .data(subjectService.getAllSubjects())
                .error(null)
                .build()
        );
    }
}