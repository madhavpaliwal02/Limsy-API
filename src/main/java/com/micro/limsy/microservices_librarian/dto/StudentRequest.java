package com.micro.limsy.microservices_librarian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequest {
    private String name;
    private String email;
    private String password;
    private String rollNo;
    private String enrollment;
    private String course;
    private String semester;
    private String gender;
}
