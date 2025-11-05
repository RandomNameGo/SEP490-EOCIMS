package com.example.sep490_eocims.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "examinee")
public class Examinee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "examinee_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "exam_room_id", nullable = false)
    private ExamRoom examRoom;

    @Column(name = "seat", length = 10)
    private String seat;

    @Column(name = "status", length = 50)
    private String status;

}
