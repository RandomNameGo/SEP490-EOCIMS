package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.ExamSessionCreateRequest;
import com.example.sep490_eocims.dto.response.ExamSessionResponse;
import com.example.sep490_eocims.dto.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExamSessionService {

    String createExamSession(ExamSessionCreateRequest examSessionCreateRequest);

    List<ExamSessionResponse> getAllExamSessions();
    
    PagedResponse<ExamSessionResponse> getAllExamSessionsPaged(Pageable pageable);
    
    PagedResponse<ExamSessionResponse> getExamSessionsByExamId(Long examId, Pageable pageable);
}