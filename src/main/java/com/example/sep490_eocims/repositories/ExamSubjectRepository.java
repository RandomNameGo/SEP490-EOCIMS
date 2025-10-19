package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.ExamSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamSubjectRepository extends JpaRepository<ExamSubject, Long> {
    List<ExamSubject> findByExamId(Long examId);
    boolean existsByExamIdAndSubjectId(Long examId, Long subjectId);
    List<ExamSubject> findByExamIdAndSubjectIdIn(Long examId, List<Long> subjectIds);
}