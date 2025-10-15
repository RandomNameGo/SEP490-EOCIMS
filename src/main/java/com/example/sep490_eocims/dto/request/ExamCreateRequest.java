package com.example.sep490_eocims.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExamCreateRequest {

    @NotNull(message = "Exam name can not be null")
    @NotEmpty(message = "Exam name can not be empty")
    private String examName;

    @NotNull(message = "Start date can not be null")
    private LocalDate startDate;

    @NotNull(message = "End date can not be null")
    private LocalDate endDate;

    @NotNull(message = "Semester can not be null")
    private long semesterId;
}
