package com.example.sep490_eocims.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class StudentImportResponse {
    private int totalRows;
    private int successfulImports;
    private int failedImports;
    private List<String> errorMessages;
    private String message;
    
    public StudentImportResponse(int totalRows, int successfulImports, int failedImports, List<String> errorMessages) {
        this.totalRows = totalRows;
        this.successfulImports = successfulImports;
        this.failedImports = failedImports;
        this.errorMessages = errorMessages;
        this.message = String.format("Import completed: %d successful, %d failed out of %d total rows", 
                successfulImports, failedImports, totalRows);
    }
}