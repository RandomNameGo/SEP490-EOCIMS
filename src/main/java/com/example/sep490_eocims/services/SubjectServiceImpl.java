package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.request.SubjectRequest;
import com.example.sep490_eocims.dto.response.SubjectResponse;
import com.example.sep490_eocims.models.Subject;
import com.example.sep490_eocims.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public String createSubject(SubjectRequest subjectRequest) {
        Subject subject = new Subject();
        subject.setSubjectCode(subjectRequest.getSubjectCode());
        subject.setSubjectName(subjectRequest.getSubjectName());
        subjectRepository.save(subject);
        return "Created subject successfully";
    }

    @Override
    public List<SubjectResponse> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(this::convertToSubjectResponse)
                .collect(Collectors.toList());
    }

    private SubjectResponse convertToSubjectResponse(Subject subject) {
        SubjectResponse response = new SubjectResponse();
        response.setId(subject.getId());
        response.setSubjectCode(subject.getSubjectCode());
        response.setSubjectName(subject.getSubjectName());
        return response;
    }
}