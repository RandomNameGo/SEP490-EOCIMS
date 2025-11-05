package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.response.ExamineeImportResponse;
import com.example.sep490_eocims.models.ExamRoom;
import com.example.sep490_eocims.models.Examinee;
import com.example.sep490_eocims.models.Student;
import com.example.sep490_eocims.repositories.ExamRoomRepository;
import com.example.sep490_eocims.repositories.ExamineeRepository;
import com.example.sep490_eocims.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamineeServiceImpl implements ExamineeService {

    private final StudentRepository studentRepository;
    private final ExamRoomRepository examRoomRepository;
    private final ExamineeRepository examineeRepository;

    @Override
    public ExamineeImportResponse importExamineesFromExcel(MultipartFile file) {
        List<String> errors = new ArrayList<>();
        List<Examinee> toSave = new ArrayList<>();
        int totalRows = 0;
        int ok = 0;
        int failed = 0;

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // skip header
                Row row = sheet.getRow(i);
                if (row == null) continue;
                totalRows++;

                try {
                    String studentId = getCellValueAsString(row.getCell(0));
                    String roomCode = getCellValueAsString(row.getCell(1));
                    String seat = getCellValueAsString(row.getCell(2));
                    String status = getCellValueAsString(row.getCell(3));

                    if (studentId == null || studentId.trim().isEmpty()) {
                        errors.add("Row " + (i + 1) + ": studentId is required");
                        failed++;
                        continue;
                    }
                    if (roomCode == null || roomCode.trim().isEmpty()) {
                        errors.add("Row " + (i + 1) + ": roomCode is required");
                        failed++;
                        continue;
                    }

                    Optional<Student> studentOpt = studentRepository.findById(studentId.trim());
                    if (studentOpt.isEmpty()) {
                        errors.add("Row " + (i + 1) + ": Student '" + studentId + "' not found");
                        failed++;
                        continue;
                    }

                    Optional<ExamRoom> examRoomOpt = examRoomRepository.findByRoomCode(roomCode.trim());
                    if (examRoomOpt.isEmpty()) {
                        errors.add("Row " + (i + 1) + ": ExamRoom with roomCode '" + roomCode + "' not found");
                        failed++;
                        continue;
                    }

                    Student student = studentOpt.get();
                    ExamRoom examRoom = examRoomOpt.get();

                    // prevent duplicates
                    if (examineeRepository.existsByStudent_StudentIdAndExamRoom_RoomCode(student.getStudentId(), examRoom.getRoomCode())) {
                        errors.add("Row " + (i + 1) + ": Examinee already exists for student '" + studentId + "' in room '" + roomCode + "'");
                        failed++;
                        continue;
                    }

                    Examinee examinee = new Examinee();
                    examinee.setStudent(student);
                    examinee.setExamRoom(examRoom);
                    examinee.setSeat(seat);
                    examinee.setStatus(status);

                    toSave.add(examinee);
                    ok++;
                } catch (Exception e) {
                    errors.add("Row " + (i + 1) + ": Error processing row - " + e.getMessage());
                    failed++;
                }
            }

            if (!toSave.isEmpty()) {
                examineeRepository.saveAll(toSave);
            }
        } catch (IOException e) {
            errors.add("Error reading Excel file: " + e.getMessage());
            return new ExamineeImportResponse(0, 0, 0, errors);
        }

        return new ExamineeImportResponse(totalRows, ok, failed, errors);
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getDateCellValue().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        yield String.valueOf((long) numericValue);
                    } else {
                        yield String.valueOf(numericValue);
                    }
                }
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> null;
        };
    }
}
