package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.Examinee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamineeRepository extends JpaRepository<Examinee, Long> {
    boolean existsByStudent_StudentIdAndExamRoom_RoomCode(String studentId, String roomCode);
}
