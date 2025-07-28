package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookDTO> getAllBooks(Pageable pageable);
    BookDTO getBookById(Long id);
    BookDTO createBook(BookDTO dto);
    BookDTO updateBook(Long id, BookDTO dto);
    void deleteBook(Long id);
}
