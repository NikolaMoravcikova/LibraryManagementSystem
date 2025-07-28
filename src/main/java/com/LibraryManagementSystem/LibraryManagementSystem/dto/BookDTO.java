package com.LibraryManagementSystem.LibraryManagementSystem.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publishedYear;
    private List<BookCopyDTO> copies;
}
