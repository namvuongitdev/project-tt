package com.example.finally1.service;

import com.example.finally1.constraint.UpdateStudent;
import com.example.finally1.dto.CourseDTO;
import com.example.finally1.dto.FilterStudentDTO;
import com.example.finally1.dto.StudentCourseDTO;
import com.example.finally1.dto.StudentDTO;
import com.example.finally1.entity.Course;
import com.example.finally1.entity.Student;
import com.example.finally1.execption.custom.ListIsEmptyExecption;
import com.example.finally1.execption.custom.UserNotFoundExection;
import com.example.finally1.mapper.CourseMapper;
import com.example.finally1.mapper.StudentMapper;
import com.example.finally1.repository.CourseRepository;
import com.example.finally1.repository.StudentRepository;
import com.example.finally1.util.file.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private Image image;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<StudentDTO> getStudents(Pageable pageable) {
        List<Student> students = studentRepository.findStudentAll(pageable).getContent();
        return studentMapper.studentsToStudentDTOs(students);
    }

    @Transactional
    public StudentDTO addStudent(StudentDTO studentDTO) {
        List<Course> courses = courseRepository.findByIds(studentDTO.getIdCourse());
        if (courses.isEmpty() || courses.size() != studentDTO.getIdCourse().size()) {
            throw new ListIsEmptyExecption("ListIsEmptyExecption");
        }
        for (int i = 0; i < courses.size(); i++) {
            StudentCourseDTO studentCourseDTO = new StudentCourseDTO();

            CourseDTO courseDTO = courseMapper.courseToCourseDTO(courses.get(i));
            studentCourseDTO.setCourseDTO(courseDTO);
            studentDTO.getStudentCourseDTOS().add(i, studentCourseDTO);
        }
        studentDTO.setImage(image.upload(studentDTO.getFile()));
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        Student studentNew = studentRepository.save(student);

        return studentMapper.studentToStudentDTO(studentRepository.getStudentById(studentNew.getId()).get());
    }

    @Transactional
    public StudentDTO updateStudent(Integer id, MultipartFile file, String data) throws JsonProcessingException {
        Optional<Student> studentOptional = studentRepository.getStudentById(id);
        if (studentOptional.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();

            StudentDTO studentDTO = objectMapper.readValue(data, StudentDTO.class);

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO, UpdateStudent.class);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            String fileName = image.upload(file);
            studentDTO.setImage(fileName);

            Student student = studentMapper.updateFromStudentDTOToStudent(studentDTO, studentOptional.get());

            return studentMapper.studentToStudentDTO(studentRepository.save(student));
        } else {
            throw new UserNotFoundExection("NotFound");
        }
    }

    @Transactional(readOnly = true)
    public List<StudentDTO> filterStudent(String data , int page , int pageSize) throws JsonProcessingException {

        StringBuffer jpql = new StringBuffer("select s from Student s join fetch s.studentCourses sc join fetch sc.id.course c where 1=1");

        ObjectMapper objectMapper = new ObjectMapper();

        // Đăng ký JavaTimeModule để hỗ trợ các kiểu dữ liệu ngày giờ của Java 8
        objectMapper.registerModule(new JavaTimeModule());

        // Bật chế độ định dạng dễ đọc (tùy chọn)
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        FilterStudentDTO filterStudentDTO = objectMapper.readValue(data, FilterStudentDTO.class);

        if (filterStudentDTO.getName() != null) {
            jpql.append(" AND s.name = :name");
        }
        if (filterStudentDTO.getStatus() != null) {
            jpql.append(" AND s.status = :status");
        }
        if (filterStudentDTO.getCode() != null) {
            jpql.append(" AND s.code = :code");
        }
        if (filterStudentDTO.getStartAt() != null && filterStudentDTO.getEndAt() != null) {
            jpql.append(" AND s.createAt between :startAt and :endAt");
        }

        TypedQuery<Student> query = entityManager.createQuery(jpql.toString(), Student.class);

        if (filterStudentDTO.getCode() != null) {
            query.setParameter("code", filterStudentDTO.getCode());
        }
        if (filterStudentDTO.getName() != null) {
            query.setParameter("name", filterStudentDTO.getName());
        }
        if (filterStudentDTO.getStatus() != null) {
            query.setParameter("status", filterStudentDTO.getStatus());
        }
        if (filterStudentDTO.getStartAt() != null && filterStudentDTO.getEndAt() != null) {
            query.setParameter("startAt", filterStudentDTO.getStartAt());
            query.setParameter("endAt", filterStudentDTO.getEndAt());
        }
        int firstResult = (page - 1) * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        return studentMapper.studentsToStudentDTOs(query.getResultList());


    }
}
