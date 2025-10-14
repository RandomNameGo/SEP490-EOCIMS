package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    boolean existsByEmail(String email);

    Optional<Staff> findByEmail(String email);
}