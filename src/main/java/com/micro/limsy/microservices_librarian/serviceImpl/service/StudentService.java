package com.micro.limsy.microservices_librarian.serviceImpl.service;

import java.util.List;

import com.micro.limsy.microservices_librarian.dto.BookResponse;
import com.micro.limsy.microservices_librarian.dto.StudentRequest;
import com.micro.limsy.microservices_librarian.dto.StudentResponse;
import com.micro.limsy.microservices_librarian.model.Student;

public interface StudentService {

    /* Create a student */
    public void createStudent(StudentRequest studentRequest);

    /* Get all Students */
    public List<StudentResponse> getAllStudents();

    /* Get a Student */
    public StudentResponse getStudent(String studentId);

    /* Update a Student */
    public StudentResponse updateStudent(StudentRequest studentRequest);

    /* Delete a Student */
    public void deleteStudent(String studentId);

    /*************************** Additional Functions ***************************/

    /* Get Student Helper */
    public Student getStudentById(String studentId);

    /* Get count for Student */
    public long getCountStudent();

    /* Get My IssuedBooks */
    public List<BookResponse> getMyIssuedBook(String studentId);

    /******************* Total Student Function ********************/
    /* Create a Total Student */
    public void createTotalStudent(StudentRequest studentRequest);

    /* Get all Students */
    public List<StudentResponse> getAllTotalStudents();

    /* Get a Student */
    public StudentResponse getTotalStudent(String studentId);

    /* Get count for Total Student */
    public long getCountTotalStudent();
}
