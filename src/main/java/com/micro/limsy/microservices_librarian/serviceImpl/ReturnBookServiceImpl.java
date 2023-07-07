package com.micro.limsy.microservices_librarian.serviceImpl;

import com.micro.limsy.microservices_librarian.dto.ReturnBookResponse;
import com.micro.limsy.microservices_librarian.model.Book;
import com.micro.limsy.microservices_librarian.model.IssuedBook;
import com.micro.limsy.microservices_librarian.model.Librarian;
import com.micro.limsy.microservices_librarian.model.ReturnBook;
import com.micro.limsy.microservices_librarian.model.Student;
import com.micro.limsy.microservices_librarian.repository.ReturnBookRepo;
import com.micro.limsy.microservices_librarian.serviceImpl.service.BookService;
import com.micro.limsy.microservices_librarian.serviceImpl.service.IssuedBookService;
import com.micro.limsy.microservices_librarian.serviceImpl.service.LibrarianService;
import com.micro.limsy.microservices_librarian.serviceImpl.service.ReturnBookService;
import com.micro.limsy.microservices_librarian.serviceImpl.service.StudentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ReturnBookServiceImpl implements ReturnBookService {

    private final ReturnBookRepo returnBookRepo;
    private final LibrarianService librarianService;
    private final StudentService studentService;
    private final BookService bookService;
    private final IssuedBookService issuedBookService;

    // private final RestTemplate restTemplate;

    /* Return a Book */
    @Override
    public void returnBook(String iBookId) {
        // Get the IssuedBook from IssuedBook Service
        IssuedBook ibook = issuedBookService.getAllIssueBooks(iBookId);

        // Checking whether already return
        for (ReturnBook rbook : this.returnBookRepo.findAll()) {
            if (rbook.getLibrarianId().equals(ibook.getLibrarianId())
                    && rbook.getStudentId().equals(ibook.getStudentId())
                    && rbook.getBookId().equals(ibook.getBookId()))
                throw new EntityExistsException("Book is already returned...");
        }

        // We need to delete that record from IssuedBook
        issuedBookService.deleteIssuedBook(iBookId);

        // And added it in a returnbook object
        ReturnBook returnBook = mapToReturnBook(ibook);
        returnBook.setRbookId(UUID.randomUUID().toString());
        returnBook.setDate(new Date());
        returnBookRepo.save(returnBook);
    }

    /* Get All ReturnBooks */
    @Override
    public List<ReturnBookResponse> getAllReturnBooks() {
        return returnBookRepo.findAll().stream().map(this::mapToReturnBookResponse).collect(Collectors.toList());
    }

    /* Get a ReturnBook */
    @Override
    public ReturnBookResponse getReturnBook(String rBookId) {
        ReturnBook rBook = getReturnedBooks(rBookId);
        return mapToReturnBookResponse(rBook);
    }

    /* Delete a ReturnBook */
    @Override
    public void deleteReturnBook(String rBookId) {
        ReturnBook rBook = getReturnedBooks(rBookId);
        returnBookRepo.delete(rBook);
    }

    /************************** Helper Function ******************/

    /* Mapping Function : ReturnBookReq -> ReturnBook */
    private ReturnBook mapToReturnBook(IssuedBook iBook) {
        return ReturnBook.builder()
                .studentId(iBook.getStudentId())
                .librarianId(iBook.getLibrarianId())
                .bookId(iBook.getBookId())
                .build();
    }

    /* Mapping Function : Librarian + Student + Book -> ReturnBook */
    private ReturnBookResponse mapToReturnBookResponse(ReturnBook rbook) {
        Librarian lib = getLibrarianByURL(rbook.getLibrarianId());
        Student stu = getStudentByURL(rbook.getStudentId());
        Book book = getBookByURL(rbook.getBookId());

        ReturnBookResponse rBookResponse = ReturnBookResponse.builder()
                // returnBook Details
                .rBookId(rbook.getRbookId())
                .date(rbook.getDate())
                // Book Details
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .edition(book.getEdition())
                // Student Details
                .sname(stu.getName())
                .rollNo(stu.getRollNo())
                .course(stu.getCourse())
                .sgender(stu.getGender())
                // Librarian Details
                .lname(lib.getName())
                .lgender(lib.getGender())
                .build();
        return rBookResponse;
    }

    /* Get Librarian By URL */
    private Librarian getLibrarianByURL(String librarianId) {
        // return restTemplate.getForObject("http://librarian-service/api/librarian/" +
        // librarianId,
        // Librarian.class);
        return librarianService.getLibrarianById(librarianId);
    }

    /* Get Student By URL */
    private Student getStudentByURL(String studentId) {
        // return restTemplate.getForObject("http://student-service/api/student/" +
        // studentId,
        // Student.class);
        return studentService.getStudentById(studentId);
    }

    /* Get Book By URL */
    private Book getBookByURL(String bookId) {
        // return restTemplate.getForObject("http://book-service/api/book/" + bookId,
        // Book.class);
        return bookService.getBookById(bookId);
    }

    /************************ Additional Functions ************************/
    /* Get All ReturnedBooks */
    @Override
    public List<ReturnBook> getAllReturnedBooks() {
        return returnBookRepo.findAll();
    }

    /* Get a ReturnedBooks */
    @Override
    public ReturnBook getReturnedBooks(String rBookId) {
        return getAllReturnedBooks().stream().filter(rbook -> rbook.getRbookId().equals(rBookId))
                .findAny().orElseThrow(() -> new EntityNotFoundException("Book not found..."));
    }

    /* Get all ReturnBooks for a Librarian */
    public List<ReturnBookResponse> getReturnBooks_Librarian(String librarianId) {
        // Filtered for a librarian & mapping to ReturnBookResponse
        List<ReturnBookResponse> rbookList = new ArrayList<>();
        for (ReturnBook rbook : this.returnBookRepo.findAll())
            if (rbook.getLibrarianId().equals(librarianId))
                rbookList.add(mapToReturnBookResponse(rbook));

        return rbookList;
    }

    /* Get Count for ReturnBooks */
    @Override
    public long getCount() {
        return this.returnBookRepo.count();
    }

}
