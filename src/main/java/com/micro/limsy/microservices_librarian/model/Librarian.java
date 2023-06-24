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

@Entity
@Table(name = "t_librarian")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libId;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String contact;
    private Date date;
}
