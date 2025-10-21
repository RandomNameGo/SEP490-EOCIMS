package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.response.PagedResponse;
import com.example.sep490_eocims.dto.response.StudentResponse;
import com.example.sep490_eocims.models.Student;
import com.example.sep490_eocims.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::convertToStudentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PagedResponse<StudentResponse> getAllStudentsPaged(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findAll(pageable);
        List<StudentResponse> studentResponses = studentPage.getContent().stream()
                .map(this::convertToStudentResponse)
                .collect(Collectors.toList());
        
        return new PagedResponse<>(
                studentResponses,
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages()
        );
    }

    private StudentResponse convertToStudentResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setStudentId(student.getStudentId());
        response.setStudentName(student.getStudentName());
        response.setStudentEmail(student.getStudentEmail());
        response.setStatus(student.getStatus());
        return response;
    }
}