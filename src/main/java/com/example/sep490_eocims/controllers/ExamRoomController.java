package com.example.sep490_eocims.controllers;

import com.example.sep490_eocims.dto.request.ExamRoomCreateRequest;
import com.example.sep490_eocims.dto.request.ExamSessionCreateRequest;
import com.example.sep490_eocims.dto.response.ApiResponse;
import com.example.sep490_eocims.repositories.ExamRoomRepository;
import com.example.sep490_eocims.services.ExamRoomService;
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
public class ExamRoomController {

    private final ExamRoomService examRoomService;

    @PostMapping("/exam-room")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addExamRoom(@RequestBody @Valid ExamRoomCreateRequest examRoomCreateRequest) {
        return ResponseEntity.ok().body(ApiResponse.<String>builder()
                .success(true)
                .message("Success")
                .data(examRoomService.autoAssignRooms(examRoomCreateRequest.getExamSessionId(), examRoomCreateRequest.getExamSubjectId(), examRoomCreateRequest.getNumberOfStudents()))
                .error(null)
                .build()
        );
    }
}
