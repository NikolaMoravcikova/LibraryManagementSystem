package com.LibraryManagementSystem.LibraryManagementSystem.controller;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.service.BookCopyService;
import com.LibraryManagementSystem.LibraryManagementSystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerUnitTest {

    @Mock
    private BookService bookService;

    @Mock
    private BookCopyService copyService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_shouldReturnList() {
        BookDTO dto = BookDTO.builder().id(1L).title("Book 1").build();
        when(bookService.getAllBooksWithoutCopies(any())).thenReturn(new org.springframework.data.domain.PageImpl<>(List.of(dto)));

        var result = bookController.getAll(null);
        assertEquals(1, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
    }

    @Test
    void getBookById_shouldReturnBook() {
        BookDTO dto = BookDTO.builder().id(1L).title("Book X").build();
        when(bookService.getBookById(1L)).thenReturn(dto);

        var result = bookController.getBook(1L);

        assertEquals("Book X", result.getTitle());
    }

    @Test
    void createBook_shouldReturnCreatedBook() {
        BookDTO dto = BookDTO.builder().title("New Book").build();
        when(bookService.createBook(dto)).thenReturn(dto);

        var result = bookController.createBook(dto);

        assertEquals("New Book", result.getTitle());
    }

    @Test
    void deleteBook_shouldCallService() {
        bookController.deleteBook(1L);
        verify(bookService, times(1)).deleteBook(1L);
    }
}