package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.UpdateBookDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.LibraryManagementSystem.exception.NotFoundException;
import com.LibraryManagementSystem.LibraryManagementSystem.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Override
    public Page<BookDTO> getAllBooksWithoutCopies(Pageable pageable) {
        return bookRepo.findAll(pageable)
                .map(book -> {
                    BookDTO dto = new BookDTO();
                    dto.setId(book.getId());
                    dto.setTitle(book.getTitle());
                    dto.setAuthor(book.getAuthor());
                    dto.setIsbn(book.getISBN());
                    dto.setPublishedYear(book.getPublishedYear());
                    return dto;
                });
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
    public BookDTO updateBook(Long id, UpdateBookDTO dto) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        if (dto.getTitle() != null) {
            book.setTitle(dto.getTitle());
        }
        if (dto.getAuthor() != null) {
            book.setAuthor(dto.getAuthor());
        }
        if (dto.getIsbn() != null) {
            book.setISBN(dto.getIsbn());
        }
        if (dto.getPublishedYear() != null) {
            book.setPublishedYear(dto.getPublishedYear());
        }

        Book updatedBook = bookRepo.save(book);
        return mapToDTO(updatedBook);
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
                .copies(Optional.ofNullable(book.getCopies())
                        .orElseGet(List::of)
                        .stream()
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
