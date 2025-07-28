package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.LibraryManagementSystem.exception.NotFoundException;
import com.LibraryManagementSystem.LibraryManagementSystem.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {

       return bookRepo.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        return mapToDTO(book);
    }

    @Override
    public BookDTO createBook(BookDTO dto) {
        Book book = mapToEntity(dto);
        return mapToDTO(bookRepo.save(book));
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setISBN(dto.getIsbn());
        book.setPublishedYear(dto.getPublishedYear());
        return mapToDTO(bookRepo.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }

    private BookDTO mapToDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getISBN())
                .publishedYear(book.getPublishedYear())
                .copies(book.getCopies().stream()
                        .map(c -> BookCopyDTO.builder().id(c.getId()).available(c.isAvailable()).build())
                        .collect(Collectors.toList()))
                .build();
    }
    private Book mapToEntity(BookDTO dto) {
        return Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .ISBN(dto.getIsbn())
                .publishedYear(dto.getPublishedYear())
                .build();
    }
}
