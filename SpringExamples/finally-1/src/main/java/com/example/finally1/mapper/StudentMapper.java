package com.example.finally1.mapper;

import com.example.finally1.dto.CourseDTO;
import com.example.finally1.dto.StudentDTO;
import com.example.finally1.entity.Student;
import com.example.finally1.util.DatetimeUtil;
import com.example.finally1.util.status.Status;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentCourseMapper.class},
        imports = {DatetimeUtil.class, Status.class})
public interface StudentMapper {

    @Mapping(source = "studentCourseDTOS", target = "studentCourses")
    @Mapping(target = "createAt", expression = "java(DatetimeUtil.getCurrentDateAndTime())")
    @Mapping(target = "updateAt", expression = "java(DatetimeUtil.getCurrentDateAndTime())")
    @Mapping(target = "status", defaultExpression = "java(Status.DANG_HOAT_DONG)")
    Student studentDTOToStudent(StudentDTO studentDTO);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateAt", expression = "java(DatetimeUtil.getCurrentDateAndTime())")
    @Mapping(source = "studentCourseDTOS", target = "studentCourses")
//    , qualifiedByName = "updateStudentCourseDTOToStundentCourse"
    Student updateFromStudentDTOToStudent(StudentDTO studentDTO, @MappingTarget Student student);

    @Mapping(target = "file", ignore = true)
//    @Mapping(source = "studentCourses" , target = "studentCourseDTOS")
    StudentDTO studentToStudentDTO(Student student);

//    @Named("loadingToCourse")
//    StudentDTO loadingToCourse(Student student);

    List<StudentDTO> studentsToStudentDTOs(List<Student> students);

    @AfterMapping
    default void afterStudentDTOToStudent(@MappingTarget Student student) {
        student.getStudentCourses().forEach(o -> {
            if (o.getId().getStudent() == null) {
                o.getId().setStudent(student);
            }
        });
    }

    @AfterMapping
    default void afterStudentToStudentDTO(@MappingTarget StudentDTO studentDTO, Student student) {
        if (student.getIsCheck()) {
            CourseMapper courseMapper = new CourseMapperImpl();
            student.getStudentCourses().forEach(o -> {
                o.getId().getCourse().setIsCheck(false);
                CourseDTO courseDTO = courseMapper.courseToCourseDTO(o.getId().getCourse());
                studentDTO.getCourseDTOS().add(courseDTO);
            });
        }
    }
}
