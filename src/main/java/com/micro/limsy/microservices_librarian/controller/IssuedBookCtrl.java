package com.micro.limsy.microservices_librarian.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.micro.limsy.microservices_librarian.dto.IssuedBookRequest;
import com.micro.limsy.microservices_librarian.dto.IssuedBookResponse;
import com.micro.limsy.microservices_librarian.model.IssuedBook;
import com.micro.limsy.microservices_librarian.serviceImpl.service.IssuedBookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issuedbook")
@RequiredArgsConstructor
public class IssuedBookCtrl {

    private final IssuedBookService issuedBookService;

    /* Issue a Book */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String issueBook(@RequestBody IssuedBookRequest issuedBookRequest) {
        System.out.println(issuedBookRequest);
        issuedBookService.issueBook(issuedBookRequest);
        return "Book Issued successfully...";
    }

    /* Get all IssuedBook */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IssuedBookResponse> getAllIssuedBooks() {
        return issuedBookService.getAllIssuedBooks();
    }

    /* Get a IssuedBook */
    @GetMapping("/{ibookId}")
    @ResponseStatus(HttpStatus.OK)
    public IssuedBookResponse getIssuedBook(@PathVariable("ibookId") String ibookId) {
        return issuedBookService.getIssuedBook(ibookId);
    }

    /* Delete a IssuedBook */
    @DeleteMapping("/{ibookId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteIssuedBook(@PathVariable("ibookId") String ibookId) {
        issuedBookService.deleteIssuedBook(ibookId);
        return "IssuedBook Deleted Successfully...";
    }

    /************************ Additional Functions ************************/

    /* Get all IssueBook */
    @GetMapping("/ib")
    @ResponseStatus(HttpStatus.OK)
    public List<IssuedBook> getAllIssueBooks() {
        return issuedBookService.getAllIssueBooks();
    }

    /* Get a IssueBook */
    @GetMapping("/ib/{ibookId}")
    @ResponseStatus(HttpStatus.OK)
    public IssuedBook getAllIssueBook(@PathVariable("ibookId") String ibookId) {
        return issuedBookService.getAllIssueBooks(ibookId);
    }

    /* Get all IssuedBooks for a Librarian */
    @GetMapping("/librarian/{librarianId}")
    @ResponseStatus(HttpStatus.OK)
    public List<IssuedBookResponse> getIssuedBooks_Librarian(@PathVariable("librarianId") String librarianId) {
        return this.issuedBookService.getIssuedBooks_Librarian(librarianId);
    }

    /* Get all IssuedBooks for a Student */
    @GetMapping("/student-ib/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<IssuedBook> getIssuedBooks_Student(@PathVariable("studentId") String studentId) {
        return this.issuedBookService.getIssuedBooks_Student(studentId);
    }

    /************************ Additional Functions ************************/
    /* Get Count for IssuedBooks */
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public long getCount() {
        return this.issuedBookService.getCount();
    }
}
