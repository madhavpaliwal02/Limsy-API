package com.micro.limsy.microservices_librarian.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibrarianResponse {
    private String libId;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String contact;
    private Date date;
}
