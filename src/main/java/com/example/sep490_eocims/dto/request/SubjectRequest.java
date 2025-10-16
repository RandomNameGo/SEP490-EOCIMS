package com.example.sep490_eocims.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectRequest {

    @NotNull(message = "Subject code can not be null")
    @NotEmpty(message = "Subject code can not be null")
    private String subjectCode;

    @NotNull(message = "Subject name can not be null")
    @NotEmpty(message = "Subject name can not be null")
    private String subjectName;

}
