package com.LibraryManagementSystem.LibraryManagementSystem.mapper;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookRequestDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookResponseDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.Book;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookRequestDTO dto);
    BookResponseDTO toDto(Book entity);
    List<BookResponseDTO> toDtoList(List<Book> books);
}