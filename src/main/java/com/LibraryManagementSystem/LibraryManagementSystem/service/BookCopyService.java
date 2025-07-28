package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyRequestDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyResponseDTO;


import java.util.List;

public interface BookCopyService {
    List<BookCopyResponseDTO> getCopiesByBookId(Long bookId);
    BookCopyResponseDTO addCopy(Long bookId);
    BookCopyResponseDTO updateCopyAvailability(Long bookId, Long copyId, BookCopyRequestDTO dto);
}
