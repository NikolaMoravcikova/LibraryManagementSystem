package com.LibraryManagementSystem.LibraryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{10}$")
    private String isbn;

    @NotNull
    @Min(1500)
    @Max(2100)
    private Integer publishedYear;

    private List<BookCopyDTO> copies;
}
