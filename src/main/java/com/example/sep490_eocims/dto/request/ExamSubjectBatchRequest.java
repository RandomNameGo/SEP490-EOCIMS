package com.example.sep490_eocims.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ExamSubjectBatchRequest {

    @NotNull(message = "Exam ID cannot be null")
    private Long examId;

    @NotNull(message = "Subject IDs cannot be null")
    @NotEmpty(message = "Subject IDs cannot be empty")
    @Valid
    private List<Long> subjectIds;
}