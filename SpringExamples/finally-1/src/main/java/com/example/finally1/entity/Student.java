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
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code" , nullable = false , unique = true)
    private String code;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "image" , nullable = false)
    private String image;

    @Column(name = "status" , nullable = false)
    private Integer status;

    @Column(name = "create_at" , updatable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "id.student" , cascade ={ CascadeType.PERSIST , CascadeType.MERGE})
    private List<StudentCourse> studentCourses = new ArrayList<>();

    @Transient
    private Boolean isCheck = true;
}
