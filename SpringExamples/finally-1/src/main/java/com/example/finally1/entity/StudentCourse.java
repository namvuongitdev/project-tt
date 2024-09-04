package com.example.finally1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourse {

    @EmbeddedId
    private CompositeKey id;

    @Column(name = "status" , nullable = false)
    private Integer status;

    @Column(name = "create_at" , updatable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
