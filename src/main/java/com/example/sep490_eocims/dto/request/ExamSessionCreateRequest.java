package com.example.sep490_eocims.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExamSessionCreateRequest {

    @NotNull(message = "Exam session name can not be null")
    @NotEmpty(message = "Exam session name not be empty")
    private String examSessionName;

    @NotNull(message = "Exam session date code can not be null")
    private LocalDate  date;

    @NotNull(message = "Start time can not be null")
    private LocalTime startTime;

    @NotNull(message = "End time can not be null")
    private LocalTime endTime;

    @NotNull(message = "Exam ID can not be null")
    private long examId;
}
