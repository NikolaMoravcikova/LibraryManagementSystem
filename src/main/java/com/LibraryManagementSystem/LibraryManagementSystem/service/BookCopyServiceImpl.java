package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyRequestDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyResponseDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.BookCopy;
import com.LibraryManagementSystem.LibraryManagementSystem.exception.BadRequestException;
import com.LibraryManagementSystem.LibraryManagementSystem.exception.NotFoundException;
import com.LibraryManagementSystem.LibraryManagementSystem.mapper.BookCopyMapper;
import com.LibraryManagementSystem.LibraryManagementSystem.repository.BookCopyRepo;
import com.LibraryManagementSystem.LibraryManagementSystem.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {

    private final BookCopyRepo copyRepo;
    private final BookRepo bookRepo;
    private final BookCopyMapper copyMapper;

    public List<BookCopyResponseDTO> getCopiesByBookId(Long bookId) {
        return copyMapper.toDtoList(copyRepo.findByBookId(bookId));
    }

    public BookCopyResponseDTO addCopy(Long bookId) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
        BookCopy copy = BookCopy.builder().book(book).available(true).build();
        return copyMapper.toDto(copyRepo.save(copy));
    }

    public BookCopyResponseDTO updateCopyAvailability(Long bookId, Long copyId, BookCopyRequestDTO dto) {
        BookCopy copy = copyRepo.findById(copyId).orElseThrow(() -> new NotFoundException("Copy not found"));
        if (!copy.getBook().getId().equals(bookId)) throw new BadRequestException("Copy does not belong to the book");
        copy.setAvailable(dto.getAvailable());
        return copyMapper.toDto(copyRepo.save(copy));
    }
}
