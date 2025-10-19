package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.ExamSessionCreateRequest;
import com.example.sep490_eocims.dto.response.ExamSessionResponse;
import com.example.sep490_eocims.models.Exam;
import com.example.sep490_eocims.models.ExamSession;
import com.example.sep490_eocims.repositories.ExamRepository;
import com.example.sep490_eocims.repositories.ExamSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamSessionServiceImpl implements ExamSessionService {

    private final ExamSessionRepository examSessionRepository;
    private final ExamRepository examRepository;

    @Override
    public String createExamSession(ExamSessionCreateRequest examSessionCreateRequest) {
        
        Optional<Exam> examOptional = examRepository.findById(examSessionCreateRequest.getExamId());
        
        if (examOptional.isEmpty()) {
            return "Exam not found";
        }

        if(examSessionCreateRequest.getDate().isBefore(examOptional.get().getStartDate()) ||
                examSessionCreateRequest.getDate().isAfter(examOptional.get().getEndDate())) {
            return "Exam session time is invalid";
        }

        ExamSession examSession = new ExamSession();
        examSession.setExamSessionName(examSessionCreateRequest.getExamSessionName());
        examSession.setDate(examSessionCreateRequest.getDate());
        examSession.setStartTime(examSessionCreateRequest.getStartTime());
        examSession.setEndTime(examSessionCreateRequest.getEndTime());
        examSession.setExam(examOptional.get());
        examSession.setStatus("Not Started");
        
        examSessionRepository.save(examSession);
        
        return "Created exam session successfully";
    }

    @Override
    public List<ExamSessionResponse> getAllExamSessions() {
        List<ExamSession> examSessions = examSessionRepository.findAll();
        return examSessions.stream()
                .map(this::convertToExamSessionResponse)
                .collect(Collectors.toList());
    }

    private ExamSessionResponse convertToExamSessionResponse(ExamSession examSession) {
        ExamSessionResponse response = new ExamSessionResponse();
        response.setId(examSession.getId());
        response.setExamSessionName(examSession.getExamSessionName());
        response.setDate(examSession.getDate());
        response.setStartTime(examSession.getStartTime());
        response.setEndTime(examSession.getEndTime());
        
        ExamSessionResponse.ExamResponseForExamSession examResponse = new ExamSessionResponse.ExamResponseForExamSession();
        examResponse.setId(examSession.getExam().getId());
        examResponse.setExamName(examSession.getExam().getExamName());
        examResponse.setStartDate(examSession.getExam().getStartDate());
        examResponse.setEndDate(examSession.getExam().getEndDate());
        examResponse.setStatus(examSession.getExam().getStatus());
        examResponse.setSemesterName(examSession.getExam().getSemester().getSemesterName());
        
        response.setExam(examResponse);
        
        return response;
    }
}