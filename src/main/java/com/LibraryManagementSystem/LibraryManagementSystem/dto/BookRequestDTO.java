package com.LibraryManagementSystem.LibraryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {
    @NotBlank
    private String title;
    @NotBlank private String author;
    @NotBlank @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$")
    private String isbn;
    @NotNull
    @Min(1450) @Max(2100)
    private Integer publishedYear;
}
