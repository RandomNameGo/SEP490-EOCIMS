package com.example.sep490_eocims.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExamRoomCreateRequest {

    @NotNull(message = "Exam session can not be null")
    private Long examSessionId;

    @NotNull(message = "Exam subject can not be null")
    private Long examSubjectId;

    @NotNull(message = "Number of student can not be null")
    @Min(value = 1, message = "Number of students must be greater than 0")
    private int numberOfStudents;
}
