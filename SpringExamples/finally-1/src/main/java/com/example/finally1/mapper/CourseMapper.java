package com.example.finally1.mapper;

import com.example.finally1.dto.CourseDTO;
import com.example.finally1.dto.StudentDTO;
import com.example.finally1.entity.Course;
import com.example.finally1.util.DatetimeUtil;
import com.example.finally1.util.status.Status;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentCourseMapper.class}, imports = {DatetimeUtil.class, Status.class}
)
public interface CourseMapper {

    @Mapping(target = "createAt", expression = "java(DatetimeUtil.getCurrentDateAndTime())")
    @Mapping(target = "updateAt", expression = "java(DatetimeUtil.getCurrentDateAndTime())")
    @Mapping(target = "status", defaultExpression = "java(Status.DANG_HOAT_DONG)")
    Course courseDTOToCourse(CourseDTO courseDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateAt", expression = "java(DatetimeUtil.getCurrentDateAndTime())")
    Course updateFromCourseDTOToCourse(CourseDTO courseDTO, @MappingTarget Course course);

    //   @Mapping(source = "studentCourses" , target = "studentCourseDTOS" ,qualifiedByName = "studentCourseToCourse")
    CourseDTO courseToCourseDTO(Course course);

    List<CourseDTO> coursesTocourseDTOs(List<Course> courses);

    @AfterMapping
    default void afterCourseToCourseDTO(@MappingTarget CourseDTO courseDTO, Course course) {
        if(course.getIsCheck()){
            StudentMapper studentMapper = new StudentMapperImpl();
            course.getStudentCourses().forEach(o -> {
                o.getId().getStudent().setIsCheck(false);
                StudentDTO studentDTO = studentMapper.studentToStudentDTO(o.getId().getStudent());
                courseDTO.getStudentDTOS().add(studentDTO);
            });
        }
    }
}
