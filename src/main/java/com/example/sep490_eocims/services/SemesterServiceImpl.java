package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.SemesterRequest;
import com.example.sep490_eocims.dto.response.SemesterResponse;
import com.example.sep490_eocims.models.Semester;
import com.example.sep490_eocims.repositories.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;

    @Override
    public String creteSemester(SemesterRequest semesterRequest) {
        Semester semester = new Semester();
        semester.setSemesterName(semesterRequest.getSemesterName());
        semester.setStartDate(semesterRequest.getStartDate());
        semester.setEndDate(semesterRequest.getEndDate());
        semester.setStatus("Created");
        semesterRepository.save(semester);
        return "Created semester successfully";
    }

    @Override
    public List<SemesterResponse> getAllSemesters() {
        List<Semester> semesters = semesterRepository.findAll();
        return semesters.stream()
                .map(this::convertToSemesterResponse)
                .collect(Collectors.toList());
    }

    private SemesterResponse convertToSemesterResponse(Semester semester) {
        SemesterResponse response = new SemesterResponse();
        response.setSemesterName(semester.getSemesterName());
        response.setStartDate(semester.getStartDate());
        response.setEndDate(semester.getEndDate());
        response.setStatus(semester.getStatus());
        return response;
    }
}
