package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.request.SemesterRequest;
import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.dto.response.PagedResponse;
import com.example.sep490_eocims.dto.response.SemesterResponse;
import com.example.sep490_eocims.services.SemesterService;
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

    @GetMapping("/semesters")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllSemesters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ResponseEntity.ok().body(ApiResponse.<PagedResponse<SemesterResponse>>builder()
                .success(true)
                .message("Success")
                .data(semesterService.getAllSemestersPaged(pageable))
                .error(null)
                .build()
        );
    }
}
