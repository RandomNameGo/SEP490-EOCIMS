package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.response.ExamineeImportResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ExamineeService {
    ExamineeImportResponse importExamineesFromExcel(MultipartFile file);
}
