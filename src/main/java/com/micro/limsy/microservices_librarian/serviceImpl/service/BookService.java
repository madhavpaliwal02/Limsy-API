package com.micro.limsy.microservices_librarian.serviceImpl.service;

import java.util.List;

import com.micro.limsy.microservices_librarian.dto.BookRequest;
import com.micro.limsy.microservices_librarian.dto.BookResponse;
import com.micro.limsy.microservices_librarian.model.Book;

public interface BookService {
    
    /* Create a Book */
    public void createBook(BookRequest bookRequest);
    
    /* Get all Books */
    public List<BookResponse> getAllBooks();
    
    /* Get a Book */
    public BookResponse getBook(String bookId);
    
    /* Update a Book */
    public BookResponse updateBook(String bookId, BookRequest bookRequest);
    
    /* Delete a Book */
    public void deleteBook(String bookId);

    /************************** Additional Functions **************************/

    public Book getBookById(String bookId);

    /* Get IssuedBooks for Student */
    public List<BookResponse> getIssuedBook_Student(String studentId);

    /* Get Count for books available */
    public long getCount();
}
