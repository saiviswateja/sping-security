package com.springsecurity.first.project.spring.security.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "sai"),
            new Student(2, "viswa"),
            new Student(3, "teja")
    );

    @GetMapping
    public List<Student> getAllStudents()
    {
        return STUDENTS;
    }
    @PostMapping
    public void registerStudent(Student student){
        System.out.println("in register student");
    }
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Student studentId)
    {
        System.out.println(studentId);
    }
    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId, Student student)
    {
        System.out.println("Student updated");
    }
}
