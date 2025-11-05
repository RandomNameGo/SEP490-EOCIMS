package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.dto.response.RoomImportResponse;
import com.example.sep490_eocims.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("eocims/api/v1")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/rooms/import")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> importRoomsFromExcel(@RequestParam("file") MultipartFile file) {
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
            RoomImportResponse importResponse = roomService.importRoomsFromExcel(file);
            return ResponseEntity.ok().body(ApiResponse.<RoomImportResponse>builder()
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
