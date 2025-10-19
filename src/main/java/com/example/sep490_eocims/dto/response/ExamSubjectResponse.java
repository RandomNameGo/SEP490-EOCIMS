package com.example.sep490_eocims.dto.response;

import lombok.Data;

@Data
public class ExamSubjectResponse {

    private Long id;

    private String subjectName;

    private ExamResponseForExamSubject exam;

    private SubjectResponseForExamSubject subject;

    @Data
    public static class ExamResponseForExamSubject {
        private Long id;
        private String examName;
        private String status;
    }

    @Data
    public static class SubjectResponseForExamSubject {
        private Long id;
        private String subjectCode;
        private String subjectName;
    }
}