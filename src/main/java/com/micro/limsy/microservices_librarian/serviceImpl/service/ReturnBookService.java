package com.micro.limsy.microservices_librarian.serviceImpl.service;

import java.util.List;

import com.micro.limsy.microservices_librarian.dto.ReturnBookResponse;
import com.micro.limsy.microservices_librarian.model.ReturnBook;

public interface ReturnBookService {

    /* Return a Book */
    public void returnBook(String iBookId);

    /* Get All ReturnBooks */
    public List<ReturnBookResponse> getAllReturnBooks();

    /* Get a ReturnBook */
    public ReturnBookResponse getReturnBook(String rBookId);

    /* Delete a ReturnBook */
    public void deleteReturnBook(String rBookId);

    /************************ Additional Functions ************************/

    /* Get All ReturnedBooks */
    public List<ReturnBook> getAllReturnedBooks();

    /* Get a ReturnedBooks */
    public ReturnBook getReturnedBooks(String rBookId);

    /* Get Count for ReturnBooks */
    public long getCount();

}
