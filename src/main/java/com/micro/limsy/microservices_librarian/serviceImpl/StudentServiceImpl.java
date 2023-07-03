package com.micro.limsy.microservices_librarian.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.micro.limsy.microservices_librarian.dto.BookResponse;
import com.micro.limsy.microservices_librarian.dto.StudentRequest;
import com.micro.limsy.microservices_librarian.dto.StudentResponse;
import com.micro.limsy.microservices_librarian.dto.User;
import com.micro.limsy.microservices_librarian.model.Student;
import com.micro.limsy.microservices_librarian.model.TotalStudent;
import com.micro.limsy.microservices_librarian.repository.StudentRepo;
import com.micro.limsy.microservices_librarian.repository.TotalStudentRepo;
import com.micro.limsy.microservices_librarian.serviceImpl.service.BookService;
import com.micro.limsy.microservices_librarian.serviceImpl.service.StudentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final TotalStudentRepo totalStudentRepo;
    // private final RestTemplate restTemplate;
    private final BookService bookService;

    /* Create a student */
    @Override
    public void createStudent(StudentRequest studentRequest) {
        // Checking whether the student is valid or not
        // for (TotalStudent stu : this.totalStudentRepo.findAll())
        // if (stu.getName() == studentRequest.getName() && stu.getRollNo() ==
        // studentRequest.getRollNo())
        // throw new EntityNotFoundException("No record found for this student");

        // Check whether student data already exists or not
        for (Student stu : this.studentRepo.findAll())
            if (stu.getName() == studentRequest.getName() && stu.getRollNo() == studentRequest.getRollNo())
                throw new EntityExistsException("Student already exist...");

        // Saving new student data
        Student student = maptoStudent(studentRequest);
        student.setStuId(UUID.randomUUID().toString());
        student.setDate(new Date());
        studentRepo.save(student);
    }

    /* Get all Students */
    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepo.findAll().stream().map(this::maptoStudentResponse).collect(Collectors.toList());
    }

    /* Get a Student */
    @Override
    public StudentResponse getStudent(String studentId) {
        Student stu = studentRepo.findAll().stream().filter(student -> student.getStuId().equals(studentId))
                .findAny().get();

        if (stu == null)
            throw new EntityNotFoundException("Student not found...");

        return maptoStudentResponse(stu);
    }

    /* Update a Student */
    @Override
    public StudentResponse updateStudent(String studentId, StudentRequest studentRequest) {
        Student oldStudent = getStudentById(studentId);

        if (oldStudent == null)
            throw new EntityNotFoundException("Student not found...");

        Student student = maptoStudent(studentRequest);
        student.setStuId(oldStudent.getStuId());
        student.setDate(oldStudent.getDate());

        studentRepo.delete(oldStudent);
        studentRepo.save(student);

        return maptoStudentResponse(student);
    }

    /* Delete a Student */
    @Override
    public void deleteStudent(String studentId) {
        Student student = studentRepo.findAll().stream().filter(stu -> stu.getStuId().equals(studentId))
                .findAny().get();

        if (student == null)
            throw new EntityNotFoundException("Student not found...");

        studentRepo.delete(student);
    }

    /**************************** Helper Functions *****************************/
    /* Mapping : StudentRequest -> Student */
    private Student maptoStudent(StudentRequest studentRequest) {
        return Student.builder()
                .name(studentRequest.getName())
                .email(studentRequest.getEmail())
                .password(studentRequest.getPassword())
                .rollNo(studentRequest.getRollNo())
                .enrollment(studentRequest.getEnrollment())
                .course(studentRequest.getCourse())
                .semester(studentRequest.getSemester())
                .gender(studentRequest.getGender())
                .build();
    }

    /* Mapping : Student -> StudentResponse */
    private StudentResponse maptoStudentResponse(Student student) {
        return StudentResponse.builder()
                .stuId(student.getStuId())
                .name(student.getName())
                .email(student.getEmail())
                .password(student.getPassword())
                .rollNo(student.getRollNo())
                .enrollment(student.getEnrollment())
                .course(student.getCourse())
                .semester(student.getSemester())
                .gender(student.getGender())
                .date(student.getDate())
                .build();
    }

    /* Mapping : StudentRequest -> Student */
    private TotalStudent maptoTotalStudent(StudentRequest studentRequest) {
        return TotalStudent.builder()
                .name(studentRequest.getName())
                .email(studentRequest.getEmail())
                .password(studentRequest.getPassword())
                .rollNo(studentRequest.getRollNo())
                .enrollment(studentRequest.getEnrollment())
                .course(studentRequest.getCourse())
                .semester(studentRequest.getSemester())
                .gender(studentRequest.getGender())
                .build();
    }

    /* Mapping : Student -> StudentResponse */
    private StudentResponse maptoStudentResponse(TotalStudent student) {
        return StudentResponse.builder()
                .stuId(student.getStuId())
                .name(student.getName())
                .email(student.getEmail())
                .password(student.getPassword())
                .rollNo(student.getRollNo())
                .enrollment(student.getEnrollment())
                .course(student.getCourse())
                .semester(student.getSemester())
                .gender(student.getGender())
                .date(student.getDate())
                .build();
    }

    /****************************
     * Additional Functions
     *****************************/

    @Override
    public Student getStudentById(String studentId) {
        return studentRepo.findAll().stream().filter(stu -> stu.getStuId().equals(studentId)).findAny()
                .orElseThrow(() -> new EntityNotFoundException("Student Not Found..."));
    }

    /* Get count for Student */
    @Override
    public long getCountStudent() {
        return this.studentRepo.count();
    }

    /* Get My IssuedBooks */
    @Override
    public List<BookResponse> getMyIssuedBook(String studentId) {
        // String url = "http://book-service/api/book/student-ib/" + studentId;
        // BookResponse[] list = restTemplate.getForObject(url, BookResponse[].class);
        List<BookResponse> list = bookService.getIssuedBook_Student(studentId);
        return list;
    }

    /******************************
     * Total Student Function
     ***************************/
    /* Create a student */
    @Override
    public void createTotalStudent(StudentRequest studentRequest) {
        TotalStudent student = maptoTotalStudent(studentRequest);
        student.setStuId(UUID.randomUUID().toString());
        student.setDate(new Date());

        totalStudentRepo.save(student);
    }

    /* Get All Total Students */
    @Override
    public List<StudentResponse> getAllTotalStudents() {
        return this.totalStudentRepo.findAll().stream().map(this::maptoStudentResponse)
                .collect(Collectors.toList());
    }

    /* Get a Total Student */
    @Override
    public StudentResponse getTotalStudent(String studentId) {
        return getAllTotalStudents().stream().filter(stu -> stu.getStuId().equals(studentId))
                .findAny().get();
    }

    /* Get count for Total Student */
    @Override
    public long getCountTotalStudent() {
        return this.totalStudentRepo.count();
    }

    @Override
    public String studentLogIn(User user) {
        Student student = studentRepo.findAll().stream()
                .filter(stu -> stu.getEmail().equals(user.getEmail()) && stu.getPassword().equals(user.getPassword()))
                .findAny().orElseThrow(() -> new EntityNotFoundException("Student doesn't exist"));

        return student.getStuId();
    }

}
