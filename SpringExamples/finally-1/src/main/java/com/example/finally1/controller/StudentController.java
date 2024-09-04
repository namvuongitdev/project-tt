package com.example.finally1.controller;

import com.example.finally1.constraint.CreateStudent;
import com.example.finally1.dto.StudentDTO;
import com.example.finally1.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public List<StudentDTO> getAll(@PageableDefault(page = 0 , size = 10) Pageable pageable){
        return studentService.getStudents(pageable);
    }

    @PostMapping("/add")
    public StudentDTO add(@Validated(value = {CreateStudent.class}) @ModelAttribute StudentDTO studentDTO){
       return studentService.addStudent(studentDTO);
    }

    @PutMapping("/update")
    public StudentDTO update(@RequestParam Integer id , @RequestParam MultipartFile file,  @RequestParam  String data) throws JsonProcessingException {
        return studentService.updateStudent(id , file ,data);
    }

    @GetMapping("/filter")
    public List<StudentDTO> filter(@RequestParam String data , @RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "10") Integer pageSize ) throws JsonProcessingException {
        return studentService.filterStudent(data , page , pageSize);
    }
}
