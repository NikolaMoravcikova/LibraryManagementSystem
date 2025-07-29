package com.LibraryManagementSystem.LibraryManagementSystem.service;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.BookCopy;
import com.LibraryManagementSystem.LibraryManagementSystem.exception.BadRequestException;
import com.LibraryManagementSystem.LibraryManagementSystem.repository.BookCopyRepo;
import com.LibraryManagementSystem.LibraryManagementSystem.repository.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookCopyServiceImplTest {

    @InjectMocks
    private BookCopyServiceImpl service;

    @Mock
    private BookRepo bookRepo;
    @Mock
    private BookCopyRepo copyRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCopiesByBookId_returnsList() {
        BookCopy copy = BookCopy.builder().id(1L).available(true).book(new Book()).build();
        when(copyRepo.findByBookId(1L)).thenReturn(List.of(copy));

        var result = service.getCopiesByBookId(1L);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());
    }

    @Test
    void addCopy_validBookId_returnsDTO() {
        Book book = Book.builder().id(1L).build();
        BookCopy copy = BookCopy.builder().id(1L).available(true).book(book).build();
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(copyRepo.save(any())).thenReturn(copy);

        BookCopyDTO result = service.addCopy(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void updateAvailability_valid_returnsUpdated() {
        Book book = Book.builder().id(1L).build();
        BookCopy copy = BookCopy.builder().id(2L).available(false).book(book).build();
        when(copyRepo.findById(2L)).thenReturn(Optional.of(copy));
        when(copyRepo.save(any())).thenReturn(copy);

        BookCopyDTO result = service.updateAvailability(1L, 2L, true);
        assertTrue(result.isAvailable());
    }

    @Test
    void updateAvailability_invalidBookId_throws() {
        Book book = Book.builder().id(1L).build();
        BookCopy copy = BookCopy.builder().id(2L).available(false).book(book).build();
        when(copyRepo.findById(2L)).thenReturn(Optional.of(copy));

        assertThrows(BadRequestException.class, () -> service.updateAvailability(99L, 2L, true));
    }
}
