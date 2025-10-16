package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.ExamSessionCreateRequest;

public interface ExamSessionService {
    String createExamSession(ExamSessionCreateRequest examSessionCreateRequest);
}