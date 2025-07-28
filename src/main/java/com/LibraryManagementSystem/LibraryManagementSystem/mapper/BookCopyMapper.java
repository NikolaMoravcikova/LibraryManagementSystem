package com.LibraryManagementSystem.LibraryManagementSystem.mapper;

import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyResponseDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.entity.BookCopy;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    BookCopyResponseDTO toDto(BookCopy entity);
    List<BookCopyResponseDTO> toDtoList(List<BookCopy> entities);
}