package com.example.sep490_eocims.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "student_activity")
public class StudentActivity {
    @Id
    @Column(name = "student_activity_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "occur_at_room")
    private ExamRoom occurAtRoom;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "occur_time")
    private Instant occurTime;

    @Column(name = "accept_time")
    private Instant acceptTime;

    @Column(name = "resolve_time")
    private Instant resolveTime;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "evidence_img")
    private String evidenceImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "created_by")
    private Staff createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "in_charge_of")
    private Staff inChargeOf;

    @Column(name = "status", length = 50)
    private String status;

}