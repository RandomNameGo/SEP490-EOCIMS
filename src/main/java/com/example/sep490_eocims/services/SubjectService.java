package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.SubjectRequest;
import com.example.sep490_eocims.dto.response.PagedResponse;
import com.example.sep490_eocims.dto.response.SubjectResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubjectService {
    String createSubject(SubjectRequest subjectRequest);
    List<SubjectResponse> getAllSubjects();
    PagedResponse<SubjectResponse> getAllSubjectsPaged(Pageable pageable);
}