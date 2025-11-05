package com.example.sep490_eocims.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class RoomImportResponse {
    private int totalRows;
    private int successfulImports;
    private int failedImports;
    private List<String> errorMessages;
}
