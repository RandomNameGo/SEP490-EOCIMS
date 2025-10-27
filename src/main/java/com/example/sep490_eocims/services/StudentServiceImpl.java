package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.response.PagedResponse;
import com.example.sep490_eocims.dto.response.StudentImportResponse;
import com.example.sep490_eocims.dto.response.StudentResponse;
import com.example.sep490_eocims.models.Student;
import com.example.sep490_eocims.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    public StudentImportResponse importStudentsFromExcel(MultipartFile file) {
        List<String> errorMessages = new ArrayList<>();
        List<Student> studentsToSave = new ArrayList<>();
        int totalRows = 0;
        int successfulImports = 0;
        int failedImports = 0;

        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            
            // Skip header row (row 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                totalRows++;
                
                try {
                    // Expected columns: Student ID (A), Student Name (B), Student Email (C), Status (D)
                    String studentId = getCellValueAsString(row.getCell(0));
                    String studentName = getCellValueAsString(row.getCell(1));
                    String studentEmail = getCellValueAsString(row.getCell(2));
                    String status = getCellValueAsString(row.getCell(3));
                    
                    // Validate required fields
                    if (studentId == null || studentId.trim().isEmpty()) {
                        errorMessages.add("Row " + (i + 1) + ": Student ID is required");
                        failedImports++;
                        continue;
                    }
                    
                    if (studentName == null || studentName.trim().isEmpty()) {
                        errorMessages.add("Row " + (i + 1) + ": Student Name is required");
                        failedImports++;
                        continue;
                    }
                    
                    // Check if student already exists
                    if (studentRepository.existsById(studentId.trim())) {
                        errorMessages.add("Row " + (i + 1) + ": Student ID '" + studentId.trim() + "' already exists");
                        failedImports++;
                        continue;
                    }
                    
                    // Create student entity
                    Student student = new Student();
                    student.setStudentId(studentId.trim());
                    student.setStudentName(studentName.trim());
                    student.setStudentEmail(studentEmail != null ? studentEmail.trim() : null);
                    student.setStatus(status != null && !status.trim().isEmpty() ? status.trim() : "Active");
                    
                    studentsToSave.add(student);
                    successfulImports++;
                    
                } catch (Exception e) {
                    errorMessages.add("Row " + (i + 1) + ": Error processing row - " + e.getMessage());
                    failedImports++;
                }
            }
            
            // Save all valid students in batch
            if (!studentsToSave.isEmpty()) {
                studentRepository.saveAll(studentsToSave);
            }
            
            workbook.close();
            
        } catch (IOException e) {
            errorMessages.add("Error reading Excel file: " + e.getMessage());
            return new StudentImportResponse(0, 0, 0, errorMessages);
        }
        
        return new StudentImportResponse(totalRows, successfulImports, failedImports, errorMessages);
    }
    
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Convert numeric to string, removing decimal if it's a whole number
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
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