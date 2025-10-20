package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.ExamCreateRequest;
import com.example.sep490_eocims.dto.response.ExamResponse;
import com.example.sep490_eocims.dto.response.PagedResponse;
import com.example.sep490_eocims.models.Exam;
import com.example.sep490_eocims.models.Semester;
import com.example.sep490_eocims.repositories.ExamRepository;
import com.example.sep490_eocims.repositories.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    private final SemesterRepository semesterRepository;

    @Override
    public String createExam(ExamCreateRequest examCreateRequest) {

        Exam exam = new Exam();

        Optional<Semester> semester = semesterRepository.findById(examCreateRequest.getSemesterId());

        if(semester.get().getStartDate().isAfter(examCreateRequest.getStartDate()) ||
                semester.get().getEndDate().isBefore(examCreateRequest.getEndDate())) {
            return "Invalid Date";
        }

        exam.setExamName(examCreateRequest.getExamName());
        exam.setStartDate(examCreateRequest.getStartDate());
        exam.setEndDate(examCreateRequest.getEndDate());
        exam.setSemester(semester.get());
        exam.setStatus("Created");
        examRepository.save(exam);

        return "Created exam successfully";
    }

    @Override
    public List<ExamResponse> getAllExams() {
        List<Exam> exams = examRepository.findAll();
        return exams.stream()
                .map(this::convertToExamResponse)
                .collect(Collectors.toList());
    }

    private ExamResponse convertToExamResponse(Exam exam) {
        ExamResponse response = new ExamResponse();
        response.setId(exam.getId());
        response.setExamName(exam.getExamName());
        response.setStartDate(exam.getStartDate());
        response.setEndDate(exam.getEndDate());
        response.setStatus(exam.getStatus());
        response.setSemester(exam.getSemester().getSemesterName());
        return response;
    }

    @Override
    public PagedResponse<ExamResponse> getAllExamsPaged(Pageable pageable) {
        Page<Exam> examPage = examRepository.findAll(pageable);
        List<ExamResponse> examResponses = examPage.getContent().stream()
                .map(this::convertToExamResponse)
                .collect(Collectors.toList());
        
        return new PagedResponse<>(
                examResponses,
                examPage.getNumber(),
                examPage.getSize(),
                examPage.getTotalElements(),
                examPage.getTotalPages()
        );
    }

    @Override
    public PagedResponse<ExamResponse> getExamsBySemesterId(Long semesterId, Pageable pageable) {
        Page<Exam> examPage = examRepository.findBySemesterId(semesterId, pageable);
        List<ExamResponse> examResponses = examPage.getContent().stream()
                .map(this::convertToExamResponse)
                .collect(Collectors.toList());
        
        return new PagedResponse<>(
                examResponses,
                examPage.getNumber(),
                examPage.getSize(),
                examPage.getTotalElements(),
                examPage.getTotalPages()
        );
    }
}
