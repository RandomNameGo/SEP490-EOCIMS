package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query("select e from Exam  e where e.semester.id = :semesterId")
    Page<Exam> findBySemesterId(Long semesterId, Pageable pageable);
}