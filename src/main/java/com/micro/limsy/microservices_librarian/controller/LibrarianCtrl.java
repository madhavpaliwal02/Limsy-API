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

import com.micro.limsy.microservices_librarian.dto.IssuedBookResponse;
import com.micro.limsy.microservices_librarian.dto.LibrarianRequest;
import com.micro.limsy.microservices_librarian.dto.LibrarianResponse;
import com.micro.limsy.microservices_librarian.dto.User;
import com.micro.limsy.microservices_librarian.serviceImpl.service.LibrarianService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/librarian")
@RequiredArgsConstructor
public class LibrarianCtrl {

    private final LibrarianService librarianService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    /* Log In Librarian */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String librarianLogIn(@RequestBody User user) {
        if (user.getEmail().equals("admin") && user.getPassword().equals("admin"))
            return "admin";
        return librarianService.librarianLogIn(user);
    }

    /* Create a Librarian */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createLibrarian(@RequestBody LibrarianRequest librarianRequest) {
        librarianService.createLibrarian(librarianRequest);
        return "Librarian Created Successfully...";
    }

    /* Get all Librarians */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LibrarianResponse> getAllLibrarian() {
        return librarianService.getAllLibrarian();
    }

    /* Get a Librarian */
    @GetMapping("/{librarianId}")
    @ResponseStatus(HttpStatus.OK)
    public LibrarianResponse getLibrarian(@PathVariable("librarianId") String librarianId) {
        return librarianService.getLibrarian(librarianId);
    }

    /* Update a Librarian */
    @PutMapping("/{librarianId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LibrarianResponse updateLibrarian(@PathVariable("librarianId") String librarianId,
            @RequestBody LibrarianRequest librarianRequest) {
        return librarianService.updateLibrarian(librarianRequest, librarianId);
    }

    /* Delete a Librarian */
    @DeleteMapping("/{librarianId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteLibrarian(@PathVariable("librarianId") String librarianId) {
        librarianService.deleteLibrarian(librarianId);
        return "Librarian Deleted Successfully...";
    }

    /* ************************* Additional Methods ************************ */
    @GetMapping("/issuedbook/{librarianId}")
    public List<IssuedBookResponse> getIssuedBooks_Librarian(@PathVariable("librarianId") String librarianId) {
        return this.librarianService.getIssuedBooks_Librarian(librarianId);
    }

    @GetMapping("/count")
    public long getCount() {
        return this.librarianService.getCount();
    }
}
