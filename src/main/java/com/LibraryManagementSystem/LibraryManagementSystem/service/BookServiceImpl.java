package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookRequestDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookResponseDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.LibraryManagementSystem.exception.NotFoundException;
import com.LibraryManagementSystem.LibraryManagementSystem.mapper.BookCopyMapper;
import com.LibraryManagementSystem.LibraryManagementSystem.mapper.BookMapper;
import com.LibraryManagementSystem.LibraryManagementSystem.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final BookMapper bookMapper;
    private final BookCopyMapper copyMapper;

    public List<BookResponseDTO> getAllBooks() {
        return bookMapper.toDtoList(bookRepo.findAll());
    }

    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        BookResponseDTO dto = bookMapper.toDto(book);
        dto.setCopies(copyMapper.toDtoList(book.getCopies()));
        return dto;
    }

    public BookResponseDTO createBook(BookRequestDTO dto) {
        Book book = bookMapper.toEntity(dto);
        return bookMapper.toDto(bookRepo.save(book));
    }

    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        if (dto.getTitle() != null) book.setTitle(dto.getTitle());
        if (dto.getAuthor() != null) book.setAuthor(dto.getAuthor());
        if (dto.getIsbn() != null) book.setISBN(dto.getIsbn());
        if (dto.getPublishedYear() != null) book.setPublishedYear(dto.getPublishedYear());
        return bookMapper.toDto(bookRepo.save(book));
    }

    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }
}
