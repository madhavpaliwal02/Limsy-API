package com.micro.limsy.microservices_librarian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.limsy.microservices_librarian.model.Librarian;

public interface LibrarianRepo extends JpaRepository<Librarian,Long> {
    
}
