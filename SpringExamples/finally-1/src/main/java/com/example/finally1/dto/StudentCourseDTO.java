package com.example.finally1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentCourseDTO {

    private StudentDTO studentDTO;
    private CourseDTO courseDTO;
    private Integer status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
