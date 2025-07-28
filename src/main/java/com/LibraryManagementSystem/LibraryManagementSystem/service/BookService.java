package com.LibraryManagementSystem.LibraryManagementSystem.service;


import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookRequestDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookResponseDTO;

import java.util.List;

public interface BookService {
    List<BookResponseDTO> getAllBooks();
    BookResponseDTO getBookById(Long id);
    BookResponseDTO createBook(BookRequestDTO dto);
    BookResponseDTO updateBook(Long id, BookRequestDTO dto);
    void deleteBook(Long id);
}
