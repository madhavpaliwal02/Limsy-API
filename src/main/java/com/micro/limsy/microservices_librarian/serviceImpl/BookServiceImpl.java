package com.micro.limsy.microservices_librarian.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.micro.limsy.microservices_librarian.dto.BookRequest;
import com.micro.limsy.microservices_librarian.dto.BookResponse;
import com.micro.limsy.microservices_librarian.model.Book;
import com.micro.limsy.microservices_librarian.model.IssuedBook;
import com.micro.limsy.microservices_librarian.repository.BookRepo;
import com.micro.limsy.microservices_librarian.repository.IssuedBookRepo;
import com.micro.limsy.microservices_librarian.serviceImpl.service.BookService;
// import com.micro.limsy.microservices_librarian.serviceImpl.service.IssuedBookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    // private final IssuedBookService issuedBookService;
    private final IssuedBookRepo issuedBookRepo;

    /* Create a Book */
    @Override
    public void createBook(BookRequest bookRequest) {
        // Checking whether data exists
        Optional<Book> oldBook = this.bookRepo.findAll().stream()
                .filter(book -> book.getTitle().equals(bookRequest.getTitle()) &&
                        book.getAuthorName().equals(bookRequest.getAuthorName()) &&
                        book.getEdition().equals(bookRequest.getEdition()))
                .findAny();

        if (oldBook.isPresent())
            throw new EntityExistsException("Book already exists...");

        // Saving new book data
        Book book = mapToBook(bookRequest);
        book.setBookId(UUID.randomUUID().toString());
        book.setDate(new Date());

        bookRepo.save(book);
    }

    /* Get all Books */
    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepo.findAll().stream().map(this::mapToBookResponse).toList();
    }

    /* Get a Book */
    @Override
    public BookResponse getBook(String bookId) {
        Book book = bookRepo.findAll().stream().filter(b -> b.getBookId().equals(bookId))
                .findAny().get();

        if (book == null)
            throw new EntityNotFoundException("Book is not available");

        return mapToBookResponse(book);
    }

    /* Update a Book */
    @Override
    public BookResponse updateBook(String bookId, BookRequest bookRequest) {
        Book oldBook = getBookById(bookId);

        // Validating book for not having duplicate details
        if (!validateBookUpdate(bookId, bookRequest))
            throw new EntityExistsException("Book is already in the library...");

        // Updating book
        Book book = mapToBook(bookRequest);
        book.setBookId(oldBook.getBookId());
        book.setDate(oldBook.getDate());

        bookRepo.delete(oldBook);
        bookRepo.save(book);

        return mapToBookResponse(book);
    }

    /* Delete a Book */
    @Override
    public void deleteBook(String bookId) {
        // Getting the book
        Book book = bookRepo.findAll().stream().filter(b -> b.getBookId().equals(bookId))
                .findAny().get();

        // Checking if exists or not
        if (book == null)
            throw new EntityNotFoundException("Book is not available");

        // Validating that it is not issued
        for (IssuedBook ib : issuedBookRepo.findAll())
            if (ib.getBookId().equals(bookId))
                throw new EntityExistsException("Book is issued by student, can't be deleted...");

        // Deleting
        bookRepo.delete(book);
    }

    /*************************** Helper Functions ***************************/
    /* Mapping : BookRequest -> Book */
    private Book mapToBook(BookRequest bookRequest) {
        return Book.builder()
                .title(bookRequest.getTitle())
                .description(bookRequest.getDescription())
                .authorName(bookRequest.getAuthorName())
                .genre(bookRequest.getGenre())
                .edition(bookRequest.getEdition())
                .publicationYear(bookRequest.getPublicationYear())
                .pages(bookRequest.getPages())
                .count(bookRequest.getCount())
                .build();
    }

    /* Mapping : Book -> BookResponse */
    private BookResponse mapToBookResponse(Book book) {
        return BookResponse.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .description(book.getDescription())
                .authorName(book.getAuthorName())
                .genre(book.getGenre())
                .edition(book.getEdition())
                .publicationYear(book.getPublicationYear())
                .pages(book.getPages())
                .count(book.getCount())
                .date(book.getDate())
                .build();

    }

    /************************** Additional Functions **************************/

    @Override
    public Book getBookById(String bookId) {
        return this.bookRepo.findAll().stream().filter(book -> book.getBookId().equals(bookId)).findAny()
                .orElseThrow(() -> new EntityNotFoundException("Book not found..."));
    }

    /* Get IssuedBooks for Student */
    @Override
    public List<BookResponse> getIssuedBook_Student(String studentId) {
        List<BookResponse> list = new ArrayList<>();

        // For all issuedBooks
        for (IssuedBook ib : issuedBookRepo.findAll()) {
            // Filtering the records on the basis of studentId
            if (ib.getStudentId().equals(studentId)) {
                // Get BookResponse for the bookId from IssuedBook
                BookResponse book = getBook(ib.getBookId());
                // Change the bookId to iBookId
                book.setBookId(ib.getIBookId());
                list.add(book);
            }
        }

        return list;
    }

    /* Get count for books */
    @Override
    public long getCount() {
        return this.bookRepo.count();
    }

    private boolean validateBookUpdate(String bookId, BookRequest bookRequest) {
        for (Book book : this.bookRepo.findAll()) {
            if (!book.getBookId().equals(bookId)
                    && book.getTitle().equals(bookRequest.getTitle())
                    && book.getAuthorName().equals(bookRequest.getAuthorName())
                    && book.getEdition().equals(bookRequest.getEdition()))
                return false;
        }
        return true;
    }

}
