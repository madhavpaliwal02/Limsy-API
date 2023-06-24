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

@Table(name = "t_books")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookId;
    private String title;
    private String description;
    private String authorName;
    private String genre;
    private String edition;
    private String publicationYear;
    private int pages;
    private int count;
    private Date date;
}
