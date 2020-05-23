package com.example.springrestexample.controller;

import com.example.springrestexample.model.Student;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class StudentController {
    static Set<Student> students = new TreeSet<>((student1, student2) -> student1.getId().compareTo(student2.getId()));

    static {
        students.add(new Student("101", "Amar", "Bangalore", LocalDate.of(2016, Month.SEPTEMBER, 29), "LKG", 15));
        students.add(new Student("102", "Akbar", "Bangalore", LocalDate.of(2011, Month.MARCH, 27), "IV", 1));
        students.add(new Student("103", "Anthony", "Bangalore", LocalDate.of(2009, Month.DECEMBER, 9), "XI", 17));
    }


    @GetMapping(value = "/paged-students", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public Page<Student> listPaginatedStudents() {
        int requestPageSize = !students.isEmpty() ? students.size() : 1;
        List<Student> mainList = new ArrayList();
        mainList.addAll(students);
        return PageableExecutionUtils.getPage(mainList, PageRequest.of(0, requestPageSize),
                students::size);
    }


    @GetMapping(value = "/students", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public Set<Student> listStudents() {
        return students;
    }

    @GetMapping("/student/{id}")
    public Set<Student> getStudentById(@PathVariable("id") String id) {
        log.info("Passed id " + id);
        Set<Student> student = students.stream().filter(enrolledStudent -> enrolledStudent.getId().equals(id)).collect(Collectors.toSet());
        return student;
    }

    @PostMapping("/student")
    public Student registerStudent(@RequestBody Student student) {
        students.forEach(enrollStudent -> {
            if (enrollStudent.getId().equals(student.getId())) {
                ResponseEntity.status(HttpStatus.CONFLICT);
                throw new RuntimeException(String.format("Student id [%s] already exist", student.getId()));
            }
        });

        students.add(student);
        return student;
    }

    @PutMapping("/student")
    public Student updateStudent(@RequestBody Student student) {
        students.forEach(enrollStudent -> {
            if (enrollStudent.getId().equals(student.getId())) {
                students.add(student);
            }
        });

        return student;
    }

    @DeleteMapping("/student")
    public Student deleteStudent(@RequestBody Student student) {
        students.forEach(enrollStudent -> {
            if (enrollStudent.getId().equals(student.getId())) {
                students.remove(student);
            }
        });

        return student;
    }

}
