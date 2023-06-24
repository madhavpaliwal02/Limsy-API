package com.micro.limsy.microservices_librarian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibrarianRequest {

    private String name;
    private String email;
    private String password;
    private String gender;
    private String contact;
}
