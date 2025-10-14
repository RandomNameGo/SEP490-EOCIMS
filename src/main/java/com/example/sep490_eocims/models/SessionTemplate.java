package com.example.sep490_eocims.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "session_template")
public class SessionTemplate {
    @Id
    @Column(name = "session_template_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "session_template_name", nullable = false)
    private String sessionTemplateName;

    @ColumnDefault("'1'")
    @Column(name = "status", columnDefinition = "tinyint UNSIGNED")
    private Short status;

}