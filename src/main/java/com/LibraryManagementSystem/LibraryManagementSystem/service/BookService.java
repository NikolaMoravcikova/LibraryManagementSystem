package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.UpdateBookDTO;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookDTO> getAllBooksWithoutCopies(Pageable pageable);
    BookDTO getBookById(Long id);
    BookDTO createBook(BookDTO dto);
    BookDTO updateBook(Long id, UpdateBookDTO dto);
    void deleteBook(Long id);
}
