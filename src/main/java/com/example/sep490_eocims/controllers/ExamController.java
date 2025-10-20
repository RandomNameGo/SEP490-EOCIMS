package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.request.ExamCreateRequest;
import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.dto.response.ExamResponse;
import com.example.sep490_eocims.dto.response.PagedResponse;
import com.example.sep490_eocims.services.ExamService;
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
    public ResponseEntity<?> getAllExams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ResponseEntity.ok().body(ApiResponse.<PagedResponse<ExamResponse>>builder()
                .success(true)
                .message("Success")
                .data(examService.getAllExamsPaged(pageable))
                .error(null)
                .build()
        );
    }

    @GetMapping("/exams/semester/{semesterId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getExamsBySemesterId(
            @PathVariable Long semesterId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ResponseEntity.ok().body(ApiResponse.<PagedResponse<ExamResponse>>builder()
                .success(true)
                .message("Success")
                .data(examService.getExamsBySemesterId(semesterId, pageable))
                .error(null)
                .build()
        );
    }
}
