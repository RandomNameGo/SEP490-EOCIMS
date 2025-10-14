package com.example.sep490_eocims.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "student_activity_log")
public class StudentActivityLog {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_activity_id", nullable = false)
    private StudentActivity studentActivity;

    @Column(name = "logged_by")
    private String loggedBy;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "status", length = 50)
    private String status;

}