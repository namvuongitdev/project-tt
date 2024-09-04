package com.example.finally1.repository;

import com.example.finally1.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student , Integer> {

    @Query(value = "select st from Student st join fetch st.studentCourses sc join fetch sc.id.course ")
    Page<Student> findStudentAll(Pageable pageable);

    @Query(value = "select st from Student st join fetch st.studentCourses sc join fetch sc.id.course where st.id = ?1 ")
    Optional<Student> getStudentById(Integer id);
}
