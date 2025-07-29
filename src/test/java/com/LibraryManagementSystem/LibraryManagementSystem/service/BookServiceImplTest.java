package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.UpdateBookDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.LibraryManagementSystem.exception.NotFoundException;
import com.LibraryManagementSystem.LibraryManagementSystem.repository.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepo bookRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooksWithoutCopies_returnsBookDTOs() {
        Book book = Book.builder().id(1L).title("Test").author("Author").ISBN("123").publishedYear(2020).build();
        when(bookRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(book)));

        var result = bookService.getAllBooksWithoutCopies(PageRequest.of(0, 10));

        assertEquals(1, result.getContent().size());
        assertEquals("Test", result.getContent().get(0).getTitle());
    }

    @Test
    void getBookById_existingId_returnsBookDTO() {
        Book book = Book.builder().id(1L).title("Test").author("Author").ISBN("123").publishedYear(2020).build();
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));

        var result = bookService.getBookById(1L);
        assertEquals("Test", result.getTitle());
    }

    @Test
    void getBookById_invalidId_throwsNotFound() {
        when(bookRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bookService.getBookById(2L));
    }

    @Test
    void createBook_savesAndReturnsDTO() {
        BookDTO dto = BookDTO.builder().title("T").author("A").isbn("I").publishedYear(2000).build();
        Book saved = Book.builder().id(10L).title("T").author("A").ISBN("I").publishedYear(2000).build();

        when(bookRepo.save(any())).thenReturn(saved);

        BookDTO result = bookService.createBook(dto);

        assertNotNull(result.getId());
        assertEquals("T", result.getTitle());
    }

    @Test
    void updateBook_validId_updatesAndReturnsDTO() {
        Book book = Book.builder().id(1L).title("Old").author("OldA").ISBN("OldI").publishedYear(1990).build();
        UpdateBookDTO update = new UpdateBookDTO();
        update.setTitle("New");
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepo.save(any())).thenReturn(book);

        BookDTO result = bookService.updateBook(1L, update);
        assertEquals("New", result.getTitle());
    }

    @Test
    void deleteBook_callsRepo() {
        bookService.deleteBook(5L);
        verify(bookRepo, times(1)).deleteById(5L);
    }
}