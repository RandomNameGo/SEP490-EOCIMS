package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.ExamSessionCreateRequest;
import com.example.sep490_eocims.dto.response.ExamSessionResponse;

import java.util.List;

public interface ExamSessionService {

    String createExamSession(ExamSessionCreateRequest examSessionCreateRequest);

    List<ExamSessionResponse> getAllExamSessions();
}