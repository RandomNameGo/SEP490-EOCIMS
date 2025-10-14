package com.example.sep490_eocims.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "session_template_position")
public class SessionTemplatePosition {
    @Id
    @Column(name = "position_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "session_template_id", nullable = false)
    private SessionTemplate sessionTemplate;

    @Column(name = "position", nullable = false)
    private String position;

    @ColumnDefault("'1'")
    @Column(name = "number_need", columnDefinition = "int UNSIGNED not null")
    private Long numberNeed;

    @ColumnDefault("'1'")
    @Column(name = "status", columnDefinition = "tinyint UNSIGNED")
    private Short status;

}