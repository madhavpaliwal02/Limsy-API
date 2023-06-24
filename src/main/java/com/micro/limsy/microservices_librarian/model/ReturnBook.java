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

@Table(name = "t_returnbook")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rbookId;
    private String studentId;
    private String librarianId;
    private String bookId;
    private Date date;
}
