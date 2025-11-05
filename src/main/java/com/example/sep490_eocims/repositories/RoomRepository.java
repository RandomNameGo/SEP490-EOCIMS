package com.example.sep490_eocims.repositories;

import com.example.sep490_eocims.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByStatus(String status);

    boolean existsByRoomCode(String roomCode);
}
