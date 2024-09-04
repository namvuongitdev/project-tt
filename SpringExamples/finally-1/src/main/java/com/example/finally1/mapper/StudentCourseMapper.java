package com.example.finally1.mapper;

import com.example.finally1.dto.StudentCourseDTO;
import com.example.finally1.entity.StudentCourse;
import com.example.finally1.util.DatetimeUtil;
import com.example.finally1.util.status.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",imports = {DatetimeUtil.class, Status.class}
, uses = {CourseMapper.class  , StudentMapper.class})
public interface StudentCourseMapper {

    @Mapping(source = "courseDTO", target = "id.course")
    @Mapping(target = "createAt", expression = "java(DatetimeUtil.getCurrentDateAndTime())")
    @Mapping(target = "updateAt", expression = "java(DatetimeUtil.getCurrentDateAndTime())")
    @Mapping(target = "status", defaultExpression = "java(Status.DANG_HOAT_DONG)")
    StudentCourse studentCourseDTOToStudentCourse(StudentCourseDTO studentCourseDTO);

//    @Mapping(target = "courseDTO", source = "id.course" , qualifiedByName = "loadingToStudent")
//    StudentCourseDTO studentCourseToStudentCourseDTO(StudentCourse studentCourse);
//
//    @Mapping(target = "studentDTO", source = "id.student" , qualifiedByName = "loadingToCourse")
//    @Named("studentCourseToCourse")
//    StudentCourseDTO studentCourseToCourse(StudentCourse studentCourse);
}
