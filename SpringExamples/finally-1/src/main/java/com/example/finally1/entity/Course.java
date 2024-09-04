package com.example.finally1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code" , nullable = false , unique = true)
    private String code;

    @Column(name = "title" , nullable = false)
    private String title;

    @Column(name = "description" , nullable = false)
    private String description;

    @Column(name = "image" , nullable = false)
    private String image;

    @Column(name = "status" , nullable = false)
    private Integer status;

    @Column(name = "create_at" , updatable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "id.course")
    private List<StudentCourse> studentCourses = new ArrayList<>();

    @Transient
    private Boolean isCheck = true;
}
