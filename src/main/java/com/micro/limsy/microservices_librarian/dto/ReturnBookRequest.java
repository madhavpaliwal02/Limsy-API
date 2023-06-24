package com.micro.limsy.microservices_librarian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBookRequest {
    private String studentId;
    private String librarianId;
    private String bookId;
}
