package com.LibraryManagementSystem.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name="book_copies")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private Boolean available = true;
}
