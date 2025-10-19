package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.ExamSubjectBatchRequest;
import com.example.sep490_eocims.dto.request.ExamSubjectRequest;
import com.example.sep490_eocims.dto.response.ExamSubjectResponse;
import com.example.sep490_eocims.models.Exam;
import com.example.sep490_eocims.models.ExamSubject;
import com.example.sep490_eocims.models.Subject;
import com.example.sep490_eocims.repositories.ExamRepository;
import com.example.sep490_eocims.repositories.ExamSubjectRepository;
import com.example.sep490_eocims.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamSubjectServiceImpl implements ExamSubjectService {

    private final ExamSubjectRepository examSubjectRepository;
    private final ExamRepository examRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public String createExamSubject(ExamSubjectRequest examSubjectRequest) {
        
        // Retrieve Exam by examId
        Optional<Exam> examOptional = examRepository.findById(examSubjectRequest.getExamId());
        if (examOptional.isEmpty()) {
            return "Exam not found";
        }
        
        // Retrieve Subject by subjectId
        Optional<Subject> subjectOptional = subjectRepository.findById(examSubjectRequest.getSubjectId());
        if (subjectOptional.isEmpty()) {
            return "Subject not found";
        }
        
        // Check if this exam-subject combination already exists
        if (examSubjectRepository.existsByExamIdAndSubjectId(examSubjectRequest.getExamId(), examSubjectRequest.getSubjectId())) {
            return "This subject is already assigned to this exam";
        }
        
        Exam exam = examOptional.get();
        Subject subject = subjectOptional.get();
        
        // Create new ExamSubject
        ExamSubject examSubject = new ExamSubject();
        examSubject.setExam(exam);
        examSubject.setSubject(subject);
        examSubject.setSubjectName(subject.getSubjectName()); // Retrieve subject name from Subject entity
        
        examSubjectRepository.save(examSubject);
        
        return "Created exam subject successfully";
    }

    @Override
    public String createMultipleExamSubjects(ExamSubjectBatchRequest examSubjectBatchRequest) {
        
        // Retrieve Exam by examId
        Optional<Exam> examOptional = examRepository.findById(examSubjectBatchRequest.getExamId());
        if (examOptional.isEmpty()) {
            return "Exam not found";
        }
        
        Exam exam = examOptional.get();
        
        // Check for existing exam-subject combinations
        List<ExamSubject> existingExamSubjects = examSubjectRepository.findByExamIdAndSubjectIdIn(
                examSubjectBatchRequest.getExamId(), 
                examSubjectBatchRequest.getSubjectIds()
        );
        
        List<Long> existingSubjectIds = existingExamSubjects.stream()
                .map(es -> es.getSubject().getId())
                .collect(Collectors.toList());
        
        List<ExamSubject> examSubjectsToSave = new ArrayList<>();
        List<String> notFoundSubjects = new ArrayList<>();
        List<String> duplicateSubjects = new ArrayList<>();
        
        // Process each subject ID
        for (Long subjectId : examSubjectBatchRequest.getSubjectIds()) {
            // Check if this subject is already assigned to this exam
            if (existingSubjectIds.contains(subjectId)) {
                duplicateSubjects.add("Subject ID: " + subjectId);
                continue;
            }
            
            Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
            
            if (subjectOptional.isEmpty()) {
                notFoundSubjects.add("Subject ID: " + subjectId);
                continue;
            }
            
            Subject subject = subjectOptional.get();
            
            // Create new ExamSubject
            ExamSubject examSubject = new ExamSubject();
            examSubject.setExam(exam);
            examSubject.setSubject(subject);
            examSubject.setSubjectName(subject.getSubjectName()); // Retrieve subject name from Subject entity
            
            examSubjectsToSave.add(examSubject);
        }
        
        // Check if any subjects were not found or duplicated
        List<String> errorMessages = new ArrayList<>();
        if (!notFoundSubjects.isEmpty()) {
            errorMessages.add("Subjects not found: " + String.join(", ", notFoundSubjects));
        }
        if (!duplicateSubjects.isEmpty()) {
            errorMessages.add("Subjects already assigned to this exam: " + String.join(", ", duplicateSubjects));
        }
        
        if (!errorMessages.isEmpty()) {
            return String.join(". ", errorMessages);
        }
        
        // Save all exam subjects in batch
        examSubjectRepository.saveAll(examSubjectsToSave);
        
        return "Created " + examSubjectsToSave.size() + " exam subjects successfully";
    }

    @Override
    public List<ExamSubjectResponse> getAllExamSubjects() {
        List<ExamSubject> examSubjects = examSubjectRepository.findAll();
        return examSubjects.stream()
                .map(this::convertToExamSubjectResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamSubjectResponse> getExamSubjectsByExamId(Long examId) {
        List<ExamSubject> examSubjects = examSubjectRepository.findByExamId(examId);
        return examSubjects.stream()
                .map(this::convertToExamSubjectResponse)
                .collect(Collectors.toList());
    }

    private ExamSubjectResponse convertToExamSubjectResponse(ExamSubject examSubject) {
        ExamSubjectResponse response = new ExamSubjectResponse();
        response.setId(examSubject.getId());
        response.setSubjectName(examSubject.getSubjectName());

        // Convert nested Exam information
        ExamSubjectResponse.ExamResponseForExamSubject examResponse = new ExamSubjectResponse.ExamResponseForExamSubject();
        examResponse.setId(examSubject.getExam().getId());
        examResponse.setExamName(examSubject.getExam().getExamName());
        examResponse.setStatus(examSubject.getExam().getStatus());
        response.setExam(examResponse);

        // Convert nested Subject information
        ExamSubjectResponse.SubjectResponseForExamSubject subjectResponse = new ExamSubjectResponse.SubjectResponseForExamSubject();
        subjectResponse.setId(examSubject.getSubject().getId());
        subjectResponse.setSubjectCode(examSubject.getSubject().getSubjectCode());
        subjectResponse.setSubjectName(examSubject.getSubject().getSubjectName());
        response.setSubject(subjectResponse);

        return response;
    }
}