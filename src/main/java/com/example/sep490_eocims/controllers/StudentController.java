package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.dto.response.PagedResponse;
import com.example.sep490_eocims.dto.response.StudentImportResponse;
import com.example.sep490_eocims.dto.response.StudentResponse;
import com.example.sep490_eocims.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("eocims/api/v1")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/students")
    @PreAuthorize("hasAnyRole('ADMIN','EXAM_MANAGER')")
    public ResponseEntity<?> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "studentId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return ResponseEntity.ok().body(ApiResponse.<PagedResponse<StudentResponse>>builder()
                .success(true)
                .message("Success")
                .data(studentService.getAllStudentsPaged(pageable))
                .error(null)
                .build()
        );
    }

    @PostMapping("/students/import")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> importStudentsFromExcel(@RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                    .success(false)
                    .message("File is empty")
                    .data(null)
                    .error("Please select a valid Excel file")
                    .build()
            );
        }
        
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
            return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                    .success(false)
                    .message("Invalid file format")
                    .data(null)
                    .error("Please upload an Excel file (.xlsx or .xls)")
                    .build()
            );
        }
        
        try {
            StudentImportResponse importResponse = studentService.importStudentsFromExcel(file);
            
            return ResponseEntity.ok().body(ApiResponse.<StudentImportResponse>builder()
                    .success(true)
                    .message("Import completed")
                    .data(importResponse)
                    .error(null)
                    .build()
            );
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.<String>builder()
                    .success(false)
                    .message("Import failed")
                    .data(null)
                    .error("Error processing file: " + e.getMessage())
                    .build()
            );
        }
    }
}