package com.example.finally1.dto;

import com.example.finally1.constraint.CreateStudent;
import com.example.finally1.constraint.UpdateStudent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StudentDTO {

    private Integer id;

    @NotBlank(message = "NotBlank",groups = {CreateStudent.class , UpdateStudent.class})
    @Length(min = 3 , max = 20 , message = "Length",groups = {CreateStudent.class , UpdateStudent.class})
    private String code;

    @NotBlank(message = "NotBlank",groups = { CreateStudent.class,  UpdateStudent.class})
    @Length(min = 10 , max = 50 , message = "Length",groups = {CreateStudent.class , UpdateStudent.class})
    private String name;

    @NotBlank(message = "NotBlank",groups = {CreateStudent.class , UpdateStudent.class})
    @Length(min = 10 , max = 50 , message = "Length",groups = {CreateStudent.class , UpdateStudent.class})
    @Email(message = "Email",groups = {CreateStudent.class , UpdateStudent.class})
    private String email;

    private String image;

    @NotNull(message = "NotNull" ,groups = {CreateStudent.class , UpdateStudent.class})
    @Positive(message = "Positive")
    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updateAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "NotNull")
    private MultipartFile file;

    @Size(message = "Size" , min = 1,groups = CreateStudent.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> idCourse = new ArrayList<>();

    @Size(message = "Size"  , min = 1 , groups = UpdateStudent.class)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<StudentCourseDTO> studentCourseDTOS = new ArrayList<>();

    private List<CourseDTO> courseDTOS = new ArrayList<>();

}
