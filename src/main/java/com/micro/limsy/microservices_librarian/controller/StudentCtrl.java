package com.micro.limsy.microservices_librarian.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.micro.limsy.microservices_librarian.dto.BookResponse;
import com.micro.limsy.microservices_librarian.dto.StudentRequest;
import com.micro.limsy.microservices_librarian.dto.StudentResponse;
import com.micro.limsy.microservices_librarian.serviceImpl.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentCtrl {

    private final StudentService studentService;

    /* Create a Student */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createStudent(@RequestBody StudentRequest studentRequest) {
        studentService.createStudent(studentRequest);
        return "Student Created Successfully...";
    }

    /* Get all Students */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    /* Get a Student */
    @GetMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse getStudent(@PathVariable("studentId") String studentId) {
        return studentService.getStudent(studentId);
    }

    /* Update a Student */
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public StudentResponse updateStudent(@RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(studentRequest);
    }

    /* Delete a Student */
    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteStudent(@PathVariable("studentId") String studentId) {
        studentService.deleteStudent(studentId);
        return "Student Deleted Sucessfully...";
    }

    /**************************** Additional Function ****************************/
    /* Get count for Student */
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public long getCount() {
        return this.studentService.getCountStudent();
    }

    /* Get My IssuedBooks */
    @GetMapping("/my-books/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getMyIssuedBook(@PathVariable("studentId") String studentId) {
        return this.studentService.getMyIssuedBook(studentId);
    }

    /*************************** Total Student Function ***************************/

    /* Create a Student */
    @PostMapping("/total")
    @ResponseStatus(HttpStatus.CREATED)
    public String createTotalStudent(@RequestBody StudentRequest studentRequest) {
        studentService.createTotalStudent(studentRequest);
        return "Student Created Successfully...";
    }

    /* Get All Total Student */
    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> getAllTotalStudents() {
        return this.studentService.getAllTotalStudents();
    }

    /* Get count for Total Student */
    @GetMapping("/total/count")
    @ResponseStatus(HttpStatus.OK)
    public long getTotalCount() {
        return this.studentService.getCountTotalStudent();
    }
}
