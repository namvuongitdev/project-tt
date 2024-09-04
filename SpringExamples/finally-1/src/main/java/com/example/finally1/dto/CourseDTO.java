package com.example.finally1.dto;

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
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CourseDTO {

    private Integer id;

    @NotBlank(message = "NotBlank")
    @Length(min = 3 , max = 20, message = "Length")
    private String code;

    @NotBlank(message = "NotBlank")
    @Length(min = 10 , max = 50 , message = "Length")
    private String title;

    @NotBlank(message = "NotBlank")
    @Length(min = 10 , max = 50 , message = "Length")
    private String description;

    private String image;

    @NotNull(message = "NotNull")
    @Positive(message = "Positive")
    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updateAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "NotNull")
    private MultipartFile file;

    private List<StudentCourseDTO> studentCourseDTOS = new ArrayList<>();

    private List<StudentDTO> studentDTOS = new ArrayList<>();
}
