package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}