package com.example.sep490_eocims.services;

import com.example.sep490_eocims.models.ExamRoom;
import com.example.sep490_eocims.models.ExamSession;
import com.example.sep490_eocims.models.ExamSubject;
import com.example.sep490_eocims.models.Room;
import com.example.sep490_eocims.repositories.ExamRoomRepository;
import com.example.sep490_eocims.repositories.ExamSessionRepository;
import com.example.sep490_eocims.repositories.ExamSubjectRepository;
import com.example.sep490_eocims.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamRoomServiceImpl implements ExamRoomService {

    private final ExamRoomRepository examRoomRepository;

    private final ExamSessionRepository examSessionRepository;

    private final ExamSubjectRepository examSubjectRepository;

    private final RoomRepository roomRepository;

    @Override
    public String autoAssignRooms(long examSessionId, long examSubjectId, int numberOfStudents) {

        List<Room> rooms = roomRepository.findAllByStatus("available");
        int requiredRooms = (int) Math.ceil((double) numberOfStudents / 24);

        ExamSession examSession = examSessionRepository.findById(examSessionId).get();

        ExamSubject examSubject = examSubjectRepository.findById(examSubjectId).get();

        List<Room> selectedRooms = rooms.stream()
                .sorted(Comparator.comparing(Room::getRoomCode))
                .limit(requiredRooms)
                .toList();

        List<ExamRoom> examRooms = new ArrayList<>();

        for (Room room : selectedRooms) {
            int assigned = Math.min(24, numberOfStudents);
            numberOfStudents -= assigned;

            ExamRoom examRoom = new ExamRoom();
            examRoom.setRoom(room);
            examRoom.setExamSession(examSession);
            examRoom.setExamSubject(examSubject);
            examRoom.setRoomCode(room.getRoomCode());
            examRoom.setSubjectName(examSubject.getSubjectName());
            examRoom.setNumberOfStudent(assigned);
            examRoom.setStartTime(examSession.getStartTime());
            examRoom.setEndTime(examSession.getEndTime());
            examRooms.add(examRoom);

            if (numberOfStudents <= 0) break;
        }

        examRoomRepository.saveAll(examRooms);

        return "Success";
    }
}
