package com.micro.limsy.microservices_librarian.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

import com.micro.limsy.microservices_librarian.dto.IssuedBookRequest;
import com.micro.limsy.microservices_librarian.dto.IssuedBookResponse;
import com.micro.limsy.microservices_librarian.model.Book;
import com.micro.limsy.microservices_librarian.model.IssuedBook;
import com.micro.limsy.microservices_librarian.model.Librarian;
import com.micro.limsy.microservices_librarian.model.Student;
import com.micro.limsy.microservices_librarian.repository.IssuedBookRepo;
import com.micro.limsy.microservices_librarian.repository.LibrarianRepo;
import com.micro.limsy.microservices_librarian.serviceImpl.service.BookService;
import com.micro.limsy.microservices_librarian.serviceImpl.service.IssuedBookService;
import com.micro.limsy.microservices_librarian.serviceImpl.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssuedBookServiceImpl implements IssuedBookService {

    private final IssuedBookRepo issuedBookRepo;
    private final LibrarianRepo librarianRepo;
    private final StudentService studentService;
    private final BookService bookService;

    // private final RestTemplate restTemplate;

    /* ************************ CRUD Functions ********************** */

    /* Issued a Book */
    @Override
    public void issueBook(IssuedBookRequest iBookRequest) {
        List<IssuedBook> list = this.issuedBookRepo.findAll();

        // Checking whether book is already issued or not
        if (list.size() > 0) {
            Optional<IssuedBook> oldBook = list.stream()
                    .filter(ibook -> ibook.getStudentId().equals(iBookRequest.getStuId()) &&
                            ibook.getBookId().equals(iBookRequest.getBookId()))
                    .findAny();

            if (oldBook.isPresent())
                throw new EntityExistsException("Book already issued...");
        }

        // Saving new issuedBook data
        IssuedBook issuedBook = mapToIssuedBook(iBookRequest);
        issuedBook.setIBookId(UUID.randomUUID().toString());
        issuedBook.setDate(new Date());
        issuedBookRepo.save(issuedBook);
    }

    /* Get all IssuedBooks */
    @Override
    public List<IssuedBookResponse> getAllIssuedBooks() {
        List<IssuedBookResponse> IssuedBookResponseList = new ArrayList<>();
        List<IssuedBook> issuedBookList = issuedBookRepo.findAll();
        if (issuedBookList.size() == 0)
            throw new EntityNotFoundException("No records found...");

        for (IssuedBook ib : issuedBookList)
            IssuedBookResponseList.add(mapToIssuedBookResponse(ib));

        // System.out.println(IssuedBookResponseList);
        return IssuedBookResponseList;
    }

    /* Get a IssuedBook */
    @Override
    public IssuedBookResponse getIssuedBook(String ibookId) {
        IssuedBookResponse issuedBookResponse = getAllIssuedBooks().stream()
                .filter(ibookRes -> ibookRes.getIBookId().equals(ibookId))
                .findAny().get();

        if (issuedBookResponse != null)
            return issuedBookResponse;
        throw new EntityNotFoundException("IssuedBook not found...");
    }

    /* Delete a IssuedBook */
    @Override
    public void deleteIssuedBook(String ibookId) {
        IssuedBook issuedBook = getAllIssueBooks(ibookId);
        issuedBookRepo.delete(issuedBook);
    }

    /************************** Helper Function ******************/

    /* Mapping Function : IssuedBookRequest -> IssuedBook */
    private IssuedBook mapToIssuedBook(IssuedBookRequest issuedBookRequest) {
        return IssuedBook.builder()
                .studentId(issuedBookRequest.getStuId())
                .bookId(issuedBookRequest.getBookId())
                .librarianId(issuedBookRequest.getLibId())
                .build();
    }

    /* Mapping Function : IssuedBook -> IssuedBookResponse */
    private IssuedBookResponse mapToIssuedBookResponse(IssuedBook ibook) {

        Librarian lib = getLibrarianByURL(ibook.getLibrarianId());
        Student stu = getStudentByURL(ibook.getStudentId());
        Book book = getBookByURL(ibook.getBookId());

        return IssuedBookResponse.builder()
                // IssuedBook Details
                .iBookId(ibook.getIBookId())
                .date(ibook.getDate())
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
    }

    /* Get Librarian By URL */
    private Librarian getLibrarianByURL(String librarianId) {
        // return restTemplate.getForObject("http://librarian-service/api/librarian/" +
        // librarianId,
        // Librarian.class);
        return librarianRepo.findAll().stream().filter(lib -> lib.getLibId().equals(librarianId)).findAny()
                .orElseThrow(() -> new EntityNotFoundException());
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

    /************************* Additional Functions **********************/

    /* Get all IssuedBook Objects */
    @Override
    public List<IssuedBook> getAllIssueBooks() {
        return issuedBookRepo.findAll();
    }

    /* Get a IssuedBook Objects */
    @Override
    public IssuedBook getAllIssueBooks(String ibookId) {
        IssuedBook iBook = getAllIssueBooks().stream().filter(ibook -> ibook.getIBookId().equals(ibookId))
                .findAny().orElseThrow(() -> new EntityNotFoundException("IssuedBook Not Found"));
        return iBook;
    }

    /* Get all IssuedBooks for a Librarian */
    @Override
    public List<IssuedBookResponse> getIssuedBooks_Librarian(String librarianId) {
        // Filtering for a librarian & Mapping IssuedBook to IssuedBookResponse
        List<IssuedBookResponse> list = new ArrayList<>();
        for (IssuedBook ib : issuedBookRepo.findAll())
            if (ib.getLibrarianId().equals(librarianId))
                list.add(mapToIssuedBookResponse(ib));

        return list;
    }

    /* Get all IssuedBooks for a Student */
    @Override
    public List<IssuedBook> getIssuedBooks_Student(String studentId) {
        List<IssuedBook> list = new ArrayList<>();

        for (IssuedBook ib : issuedBookRepo.findAll())
            if (ib.getStudentId().equals(studentId))
                list.add(ib);

        return list;
    }

    /* Get Count for IssuedBooks */
    @Override
    public long getCount() {
        return this.issuedBookRepo.count();
    }

}
