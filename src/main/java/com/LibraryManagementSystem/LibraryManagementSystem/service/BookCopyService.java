package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyDTO;
import java.util.List;

public interface BookCopyService {
    List<BookCopyDTO> getCopiesByBookId(Long bookId);
    BookCopyDTO addCopy(Long bookId);
    BookCopyDTO updateAvailability(Long bookId, Long copyId, boolean available);
}
