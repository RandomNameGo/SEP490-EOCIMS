package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.ExamSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
    Page<ExamSession> findByExamId(Long examId, Pageable pageable);
}