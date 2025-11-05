package com.example.sep490_eocims.services;

public interface ExamRoomService {
    String autoAssignRooms(long examSessionId, long examSubjectId, int numberOfStudents);
}
