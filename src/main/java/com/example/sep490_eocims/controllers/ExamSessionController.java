package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.request.ExamSessionCreateRequest;
import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.dto.response.ExamSessionResponse;
import com.example.sep490_eocims.dto.response.PagedResponse;
import com.example.sep490_eocims.services.ExamSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<?> getAllExamSessions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ResponseEntity.ok().body(ApiResponse.<PagedResponse<ExamSessionResponse>>builder()
                .success(true)
                .message("Success")
                .data(examSessionService.getAllExamSessionsPaged(pageable))
                .error(null)
                .build()
        );
    }

    @GetMapping("/exam-sessions/exam/{examId}")
    @PreAuthorize("hasAnyRole('ADMIN','EXAM_MANAGER')")
    public ResponseEntity<?> getExamSessionsByExamId(
            @PathVariable Long examId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ResponseEntity.ok().body(ApiResponse.<PagedResponse<ExamSessionResponse>>builder()
                .success(true)
                .message("Success")
                .data(examSessionService.getExamSessionsByExamId(examId, pageable))
                .error(null)
                .build()
        );
    }
}