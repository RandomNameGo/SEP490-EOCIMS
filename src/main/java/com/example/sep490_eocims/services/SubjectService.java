package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.SubjectRequest;
import com.example.sep490_eocims.dto.response.SubjectResponse;

import java.util.List;

public interface SubjectService {
    String createSubject(SubjectRequest subjectRequest);
    List<SubjectResponse> getAllSubjects();
}