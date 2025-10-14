package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.SemesterRequest;
import com.example.sep490_eocims.mappers.SemesterMapper;
import com.example.sep490_eocims.models.Semester;
import com.example.sep490_eocims.repositories.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;

    private final SemesterMapper semesterMapper;

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
}
