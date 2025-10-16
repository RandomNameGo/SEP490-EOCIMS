package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
}