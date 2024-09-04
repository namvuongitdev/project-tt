package com.example.finally1.repository;

import com.example.finally1.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course , Integer> {

    @Query(value = "select cr from Course cr left join fetch cr.studentCourses sc left join fetch sc.id.student")
    Page<Course> getAllCourse(Pageable pageable);

    @Query(value = "select cr from Course cr join fetch cr.studentCourses sc  join fetch sc.id.student where cr.id in ?1")
    List<Course> findByIds(List<Integer> id);

    @Query(value = "select cr from Course cr  join fetch cr.studentCourses sc  join fetch sc.id.student where cr.id = ?1")
    Optional<Course> getCourseById(Integer id);
}
