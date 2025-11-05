package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.ExamRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRoomRepository extends JpaRepository<ExamRoom, Long> {
    Optional<ExamRoom> findByRoomCode(String roomCode);
}
