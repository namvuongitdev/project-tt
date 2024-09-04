package com.example.finally1.controller;

import com.example.finally1.dto.CourseDTO;
import com.example.finally1.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @GetMapping("")
    public List<CourseDTO> getAll(@PageableDefault(page = 0 , size = 10 ) Pageable pageable){
        return courseService.getCourses(pageable);
    }

    @PostMapping(value = "/add")
    public CourseDTO add(@Valid @ModelAttribute CourseDTO courseDTO) {
       return courseService.addCourse(courseDTO.getFile(), courseDTO);
    }

    @PutMapping(value = "/update")
    public CourseDTO update(@Valid @ModelAttribute CourseDTO courseDTO,@RequestParam Integer id){
        return courseService.updateCourse(courseDTO , id);
    }
}
