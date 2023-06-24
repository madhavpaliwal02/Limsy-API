package com.micro.limsy.microservices_librarian.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.micro.limsy.microservices_librarian.dto.IssuedBookResponse;
import com.micro.limsy.microservices_librarian.dto.LibrarianRequest;
import com.micro.limsy.microservices_librarian.dto.LibrarianResponse;
import com.micro.limsy.microservices_librarian.dto.User;
import com.micro.limsy.microservices_librarian.exception.ResourceNotFoundException;
import com.micro.limsy.microservices_librarian.model.Librarian;
import com.micro.limsy.microservices_librarian.repository.LibrarianRepo;
import com.micro.limsy.microservices_librarian.serviceImpl.service.IssuedBookService;
import com.micro.limsy.microservices_librarian.serviceImpl.service.LibrarianService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibrarianServiceImpl implements LibrarianService {

    private final LibrarianRepo librarianRepo;
    private final IssuedBookService issuedBookService;
    // private final RestTemplate restTemplate;
    // private String url = "";

    /*
     * ******************************** CRUD Functions ***************************
     */

    /* Create a Librarian */
    @Override
    public void createLibrarian(LibrarianRequest librarianReq) {
        // Validating whether librarian exists or not
        if (librarianRepo.findAll().stream().filter(
                lib -> lib.getEmail().equals(librarianReq.getEmail())
                        && lib.getName().equals(librarianReq.getName())
                        && lib.getPassword().equals(librarianReq.getPassword()))
                .findAny().isPresent())
            throw new EntityExistsException();

        // If not found then create one
        Librarian librarian = mapToLibrarian(librarianReq);
        librarian.setLibId(UUID.randomUUID().toString());
        librarian.setDate(new Date());

        librarianRepo.save(librarian);
    }

    /* Get all Librarians */
    @Override
    public List<LibrarianResponse> getAllLibrarian() {
        return librarianRepo.findAll().stream().map(this::mapToLibrarianResponse).collect(Collectors.toList());
    }

    /* Get a Librarian */
    @Override
    public LibrarianResponse getLibrarian(String librarianId) {
        Librarian librarian = getLibrarianById(librarianId);
        return mapToLibrarianResponse(librarian);
    }

    /* Update a Librarian */
    @Override
    public LibrarianResponse updateLibrarian(LibrarianRequest librarianRequest, String librarianId) {
        // Old details, to be update
        Librarian oldLib = getLibrarianById(librarianId);

        // Updating details
        Librarian lib = mapToLibrarian(librarianRequest);
        lib.setLibId(oldLib.getLibId());
        lib.setDate(oldLib.getDate());

        librarianRepo.save(lib); // Saving new details
        librarianRepo.delete(oldLib); // Deleting old details

        return mapToLibrarianResponse(lib);
    }

    /* Delete a Librarian */
    @Override
    public void deleteLibrarian(String librarianId) {
        Librarian lib = getLibrarianById(librarianId);
        librarianRepo.delete(lib);
    }

    /********************************
     * Helper Functions
     *******************************/

    /* Get Librarian Helper */
    @Override
    public Librarian getLibrarianById(String librarianId) {
        return this.librarianRepo.findAll().stream().filter(lib -> lib.getLibId().equals(librarianId))
                .findAny().orElseThrow(() -> new ResourceNotFoundException("Librarian", "librarianId", librarianId));
    }

    /* Mapping Func : Lib -> LibRes */
    private LibrarianResponse mapToLibrarianResponse(Librarian librarian) {
        return LibrarianResponse.builder()
                .libId(librarian.getLibId())
                .name(librarian.getName())
                .email(librarian.getEmail())
                .password(librarian.getPassword())
                .gender(librarian.getGender())
                .contact(librarian.getContact())
                .date(librarian.getDate())
                .build();
    }

    /* Mapping Func : LibReq -> Lib */
    private Librarian mapToLibrarian(LibrarianRequest librarianRequest) {
        return Librarian.builder()
                .name(librarianRequest.getName())
                .email(librarianRequest.getEmail())
                .password(librarianRequest.getPassword())
                .gender(librarianRequest.getGender())
                .contact(librarianRequest.getContact())
                .build();
    }

    /*
     ***************************** Additional Functions *****************************
     */

    /* Get All IssuedBooks for a Librarian : Student + Book */
    @Override
    public List<IssuedBookResponse> getIssuedBooks_Librarian(String librarianId) {
        // url = "http://issuedbook-service/api/issuedbook/librarian/" + librarianId;
        // Getting all Issuedbook objects
        // IssuedBookResponse[] ibr = restTemplate.getForObject(url,
        // IssuedBookResponse[].class);
        List<IssuedBookResponse> list = issuedBookService.getIssuedBooks_Librarian(librarianId);
        return list;
    }

    /* Get count for Librarians */
    @Override
    public long getCount() {
        return this.librarianRepo.count();
    }

    @Override
    public String librarianLogIn(User user) {
        Librarian librarian = librarianRepo.findAll().stream().filter(lib -> lib.getEmail().equals(user.getEmail())
                && lib.getPassword().equals(user.getPassword())).findAny()
                .orElseThrow(() -> new EntityNotFoundException("Invalid Username or Password..."));

        return librarian.getLibId();
    }

}
