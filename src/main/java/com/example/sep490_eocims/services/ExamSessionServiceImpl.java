package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.ExamSessionCreateRequest;
import com.example.sep490_eocims.models.Exam;
import com.example.sep490_eocims.models.ExamSession;
import com.example.sep490_eocims.repositories.ExamRepository;
import com.example.sep490_eocims.repositories.ExamSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}