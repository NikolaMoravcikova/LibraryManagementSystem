package com.LibraryManagementSystem.LibraryManagementSystem.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyDTO {
    private Long id;
    private boolean available;
}
