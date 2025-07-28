package com.LibraryManagementSystem.LibraryManagementSystem.controller;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyRequestDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyResponseDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookRequestDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookResponseDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.service.BookCopyService;
import com.LibraryManagementSystem.LibraryManagementSystem.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookCopyService copyService;

    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponseDTO getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookResponseDTO createBook(@RequestBody @Valid BookRequestDTO dto) {
        return bookService.createBook(dto);
    }

    @PutMapping("/{id}")
    public BookResponseDTO updateBook(@PathVariable Long id, @RequestBody BookRequestDTO dto) {
        return bookService.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/{id}/copies")
    public List<BookCopyResponseDTO> getCopies(@PathVariable Long id) {
        return copyService.getCopiesByBookId(id);
    }

    @PostMapping("/{id}/copies")
    public BookCopyResponseDTO addCopy(@PathVariable Long id) {
        return copyService.addCopy(id);
    }

    @PutMapping("/{id}/copies/{copyId}")
    public BookCopyResponseDTO updateCopy(@PathVariable Long id, @PathVariable Long copyId,
                                          @RequestBody @Valid BookCopyRequestDTO dto) {
        return copyService.updateCopyAvailability(id, copyId, dto);
    }
}