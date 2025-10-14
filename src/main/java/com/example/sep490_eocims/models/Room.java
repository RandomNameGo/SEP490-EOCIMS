package com.example.sep490_eocims.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "room_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "room_code", nullable = false, length = 50)
    private String roomCode;

    @Column(name = "capacity", columnDefinition = "int UNSIGNED")
    private Long capacity;

    @Column(name = "status", length = 50)
    private String status;

}