package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.ExamCreateRequest;
import com.example.sep490_eocims.dto.response.ExamResponse;

import java.util.List;

public interface ExamService {

    String createExam(ExamCreateRequest examCreateRequest);
    
    List<ExamResponse> getAllExams();

}
