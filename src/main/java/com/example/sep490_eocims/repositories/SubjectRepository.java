package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}