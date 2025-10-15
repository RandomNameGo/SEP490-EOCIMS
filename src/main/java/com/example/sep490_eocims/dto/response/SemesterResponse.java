package com.example.sep490_eocims.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SemesterResponse {

    private String semesterName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

}
