package com.example.finally1.service;

import com.example.finally1.dto.CourseDTO;
import com.example.finally1.entity.Course;
import com.example.finally1.execption.custom.UserNotFoundExection;
import com.example.finally1.mapper.CourseMapper;
import com.example.finally1.repository.CourseRepository;
import com.example.finally1.util.file.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private Image image;

    @Transactional(readOnly = true)
    public List<CourseDTO> getCourses(Pageable pageable) {
        List<Course> courses = courseRepository.getAllCourse(pageable).getContent();
        return courseMapper.coursesTocourseDTOs(courses);
    }

    @Transactional
    public CourseDTO addCourse(MultipartFile file, CourseDTO courseDTO) {
        courseDTO.setImage(image.upload(file));
        Course course = courseRepository.save(courseMapper.courseDTOToCourse(courseDTO));
        return courseMapper.courseToCourseDTO(course);
    }

    @Transactional
    public  CourseDTO updateCourse(CourseDTO courseDTO ,Integer id){
        Optional<Course> courseOptional = courseRepository.getCourseById(id);
        if(courseOptional.isPresent()){
            courseDTO.setImage(image.upload(courseDTO.getFile()));
            Course course = courseMapper.updateFromCourseDTOToCourse(courseDTO , courseOptional.get());
            courseRepository.save(course);
            return courseMapper.courseToCourseDTO(courseOptional.get());
        }else{
            throw new UserNotFoundExection("NotFound");
        }
    }

//    public List<CourseDTO> filterCourse(String data , Integer page , Integer pageSize){
//
//    }

}
