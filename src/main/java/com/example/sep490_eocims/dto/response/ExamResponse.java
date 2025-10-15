package com.example.sep490_eocims.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExamResponse {

    private Long id;

    private String examName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private String semesterName;

}