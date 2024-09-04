package com.example.finally1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterStudentDTO {
    private String code;
    private String name;
    private Integer status;

    private LocalDateTime startAt;

    private LocalDateTime endAt;
}
