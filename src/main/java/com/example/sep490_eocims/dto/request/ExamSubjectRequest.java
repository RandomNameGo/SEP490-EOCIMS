package com.example.sep490_eocims.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExamSubjectRequest {

    @NotNull(message = "Subject can not be null")
    private Long subjectId;

    @NotNull(message = "Exam can not be null")
    private Long examId;
}
