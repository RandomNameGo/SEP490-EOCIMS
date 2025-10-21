package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}