package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
}