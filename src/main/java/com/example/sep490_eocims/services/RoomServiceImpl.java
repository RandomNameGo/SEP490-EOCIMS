package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.response.RoomImportResponse;
import com.example.sep490_eocims.models.Room;
import com.example.sep490_eocims.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public RoomImportResponse importRoomsFromExcel(MultipartFile file) {
        List<String> errorMessages = new ArrayList<>();
        List<Room> roomsToSave = new ArrayList<>();
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
                    // Expected columns: Room Code (A), Capacity (B), Status (C - optional)
                    String roomCode = getCellValueAsString(row.getCell(0));
                    String capacityStr = getCellValueAsString(row.getCell(1));
                    String status = getCellValueAsString(row.getCell(2));

                    if (roomCode == null || roomCode.trim().isEmpty()) {
                        errorMessages.add("Row " + (i + 1) + ": Room Code is required");
                        failedImports++;
                        continue;
                    }

                    Long capacity = null;
                    if (capacityStr != null && !capacityStr.trim().isEmpty()) {
                        try {
                            capacity = Long.parseLong(capacityStr.trim());
                            if (capacity < 0) {
                                throw new NumberFormatException("Capacity must be non-negative");
                            }
                        } catch (NumberFormatException nfe) {
                            errorMessages.add("Row " + (i + 1) + ": Invalid capacity '" + capacityStr + "'");
                            failedImports++;
                            continue;
                        }
                    }

                    // Check if room already exists by roomCode
                    if (roomRepository.existsByRoomCode(roomCode.trim())) {
                        errorMessages.add("Row " + (i + 1) + ": Room code '" + roomCode.trim() + "' already exists");
                        failedImports++;
                        continue;
                    }

                    Room room = new Room();
                    room.setRoomCode(roomCode.trim());
                    room.setCapacity(capacity);
                    room.setStatus(status != null && !status.trim().isEmpty() ? status.trim() : "available");

                    roomsToSave.add(room);
                    successfulImports++;
                } catch (Exception e) {
                    errorMessages.add("Row " + (i + 1) + ": Error processing row - " + e.getMessage());
                    failedImports++;
                }
            }

            if (!roomsToSave.isEmpty()) {
                roomRepository.saveAll(roomsToSave);
            }

            workbook.close();
        } catch (IOException e) {
            errorMessages.add("Error reading Excel file: " + e.getMessage());
            return new RoomImportResponse(0, 0, 0, errorMessages);
        }

        return new RoomImportResponse(totalRows, successfulImports, failedImports, errorMessages);
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
}
