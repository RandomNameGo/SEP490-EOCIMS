package com.example.sep490_eocims.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SemesterRequest {

    @NotNull(message = "Semester name can not be null")
    @NotEmpty(message = "Semester name can not be empty")
    private String semesterName;

    @NotNull(message = "Semester start date can not be null")
    private LocalDate startDate;

    @NotNull(message = "Semester end date can not be null")
    private LocalDate endDate;
}
