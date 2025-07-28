package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.BookCopy;
import com.LibraryManagementSystem.LibraryManagementSystem.exception.BadRequestException;
import com.LibraryManagementSystem.LibraryManagementSystem.exception.NotFoundException;
import com.LibraryManagementSystem.LibraryManagementSystem.repository.BookCopyRepo;
import com.LibraryManagementSystem.LibraryManagementSystem.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {

    private final BookCopyRepo copyRepo;
    private final BookRepo bookRepo;

    @Override
    public List<BookCopyDTO> getCopiesByBookId(Long bookId) {
        return copyRepo.findByBookId(bookId).stream()
                .map(c -> BookCopyDTO.builder().id(c.getId()).available(c.isAvailable()).build())
                .collect(Collectors.toList());
    }

    @Override
    public BookCopyDTO addCopy(Long bookId) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
        BookCopy copy = BookCopy.builder().book(book).available(true).build();
        return BookCopyDTO.builder().id(copyRepo.save(copy).getId()).available(true).build();
    }

    @Override
    public BookCopyDTO updateAvailability(Long bookId, Long copyId, boolean available) {
        BookCopy copy = copyRepo.findById(copyId).orElseThrow(() -> new NotFoundException("Copy not found"));
        if (!copy.getBook().getId().equals(bookId)) throw new BadRequestException("Copy does not belong to the book");
        copy.setAvailable(available);
        return BookCopyDTO.builder().id(copyRepo.save(copy).getId()).available(available).build();
    }
}
