package com.micro.limsy.microservices_librarian.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.micro.limsy.microservices_librarian.dto.ReturnBookResponse;
import com.micro.limsy.microservices_librarian.model.ReturnBook;
import com.micro.limsy.microservices_librarian.serviceImpl.ReturnBookServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/returnbook")
@RequiredArgsConstructor
public class ReturnBookCtrl {

    private final ReturnBookServiceImpl returnBookService;

    /* Return a Book */
    @PostMapping("/{iBookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String returnBook(@PathVariable("iBookId") String iBookId) {
        returnBookService.returnBook(iBookId);
        return "Book Returned Successfully...";
    }

    /* Get All Return Books */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReturnBookResponse> getAllReturnBooks() {
        return returnBookService.getAllReturnBooks();
    }

    /* Get a Return Book */
    @GetMapping("/{rBookId}")
    public ReturnBookResponse getReturnBook(@PathVariable("rBookId") String rBookId) {
        return returnBookService.getReturnBook(rBookId);
    }

    /* Delete a Return Book */
    @DeleteMapping("/{rBookId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteReturnBook(@PathVariable("rBookId") String rBookId) {
        returnBookService.deleteReturnBook(rBookId);
        return "Deleted Successfully...";
    }

    /************************ Additional Functions ************************/

    /* Get All Returned Books */
    @GetMapping("/rb")
    @ResponseStatus(HttpStatus.OK)
    public List<ReturnBook> getAllReturnedBooks() {
        return returnBookService.getAllReturnedBooks();
    }

    /* Get a Returned Books */
    @GetMapping("/rb/{rBookId}")
    @ResponseStatus(HttpStatus.OK)
    public ReturnBook getReturnedBooks(@PathVariable("rBookId") String rBookId) {
        return returnBookService.getReturnedBooks(rBookId);
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    /* Get Count for ReturnBooks */
    public long getCount() {
        return this.returnBookService.getCount();
    }

}
