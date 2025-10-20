package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.ExamCreateRequest;
import com.example.sep490_eocims.dto.response.ExamResponse;
import com.example.sep490_eocims.dto.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExamService {

    String createExam(ExamCreateRequest examCreateRequest);
    
    List<ExamResponse> getAllExams();
    
    PagedResponse<ExamResponse> getAllExamsPaged(Pageable pageable);
    
    PagedResponse<ExamResponse> getExamsBySemesterId(Long semesterId, Pageable pageable);

}
