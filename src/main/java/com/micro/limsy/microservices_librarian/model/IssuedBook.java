package com.micro.limsy.microservices_librarian.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="t_issuedbook")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssuedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iBookId;
    private String studentId;
    private String bookId;
    private String librarianId;
    private Date date;
}
