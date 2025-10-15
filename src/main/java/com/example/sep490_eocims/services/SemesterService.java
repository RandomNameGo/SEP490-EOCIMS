package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.SemesterRequest;
import com.example.sep490_eocims.dto.response.SemesterResponse;

import java.util.List;

public interface SemesterService {
    String creteSemester(SemesterRequest semesterRequest);
    List<SemesterResponse> getAllSemesters();
}
