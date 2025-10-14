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
@Table(name = "session_incident")
public class SessionIncident {
    @Id
    @Column(name = "session_incident_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "exam_session_id", nullable = false)
    private ExamSession examSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "occur_at_room")
    private ExamRoom occurAtRoom;

    @Column(name = "incident_name")
    private String incidentName;

    @Column(name = "occur_time")
    private Instant occurTime;

    @Column(name = "accept_time")
    private Instant acceptTime;

    @Column(name = "resolve_time")
    private Instant resolveTime;

    @Lob
    @Column(name = "description")
    private String description;

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