package com.example.sep490_eocims.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExamSessionResponse {

    private long id;

    private String examSessionName;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private ExamResponseForExamSession Exam;


    @Data
    public static class ExamResponseForExamSession {
        private Long id;

        private String examName;

        private LocalDate startDate;

        private LocalDate endDate;

        private String status;

        private String semesterName;
    }
}
