package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.response.PagedResponse;
import com.example.sep490_eocims.dto.response.StudentImportResponse;
import com.example.sep490_eocims.dto.response.StudentResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllStudents();
    PagedResponse<StudentResponse> getAllStudentsPaged(Pageable pageable);
    StudentImportResponse importStudentsFromExcel(MultipartFile file);
}