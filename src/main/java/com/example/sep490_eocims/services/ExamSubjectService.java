package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.ExamSubjectBatchRequest;
import com.example.sep490_eocims.dto.request.ExamSubjectRequest;
import com.example.sep490_eocims.dto.response.ExamSubjectResponse;

import java.util.List;

public interface ExamSubjectService {

    String createExamSubject(ExamSubjectRequest examSubjectRequest);

    String createMultipleExamSubjects(ExamSubjectBatchRequest examSubjectBatchRequest);

    List<ExamSubjectResponse> getAllExamSubjects();

    List<ExamSubjectResponse> getExamSubjectsByExamId(Long examId);
}