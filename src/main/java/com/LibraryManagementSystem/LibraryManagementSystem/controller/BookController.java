package com.LibraryManagementSystem.LibraryManagementSystem.controller;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.service.BookCopyService;
import com.LibraryManagementSystem.LibraryManagementSystem.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookCopyService copyService;

    @GetMapping
    public List<BookDTO> getAll(Pageable pageable) {

        return bookService.getAllBooks(pageable).getContent();
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id) {

        return bookService.getBookById(id);
    }

    @PostMapping
    public BookDTO createBook(@RequestBody @Valid BookDTO dto) {

        return bookService.createBook(dto);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO dto) {
        return bookService.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);
    }

    @GetMapping("/{id}/copies")
    public List<BookCopyDTO> getCopies(@PathVariable Long id) {

        return copyService.getCopiesByBookId(id);
    }

    @PostMapping("/{id}/copies")
    public BookCopyDTO addCopy(@PathVariable Long id) {
        return copyService.addCopy(id);
    }

    @PutMapping("/{id}/copies/{copyId}")
    public BookCopyDTO updateCopy(@PathVariable Long id, @PathVariable Long copyId,
                                          @RequestBody @Valid BookCopyDTO dto) {
        return copyService.updateAvailability(id, copyId, dto.isAvailable());
    }
}