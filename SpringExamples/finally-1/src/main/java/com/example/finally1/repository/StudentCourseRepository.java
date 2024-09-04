package com.example.finally1.repository;

import com.example.finally1.entity.CompositeKey;
import com.example.finally1.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository extends JpaRepository<StudentCourse , CompositeKey> {
}
