package com.LibraryManagementSystem.LibraryManagementSystem.controller;


import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookCopyDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.BookDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.dto.UpdateBookDTO;
import com.LibraryManagementSystem.LibraryManagementSystem.service.BookCopyService;
import com.LibraryManagementSystem.LibraryManagementSystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookViewController {

    private final BookService bookService;
    private final BookCopyService copyService;

    @GetMapping
    public String listBooks(Model model) {
        var page = bookService.getAllBooksWithoutCopies(PageRequest.of(0, 10));
        model.addAttribute("books", page.getContent());
        return "books"; // books.html
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        BookDTO book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "bookDetail"; // bookDetail.html
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new BookDTO());
        return "addBook"; // addBook.html
    }

    @PostMapping
    public String addBook(@ModelAttribute BookDTO bookDTO) {
        bookService.createBook(bookDTO);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("bookForm", bookService.getBookById(id));
        return "editBook"; // editBook.html
    }

    @PostMapping("/{id}/edit")
    public String editBook(@PathVariable Long id, @ModelAttribute BookDTO bookForm) {
        UpdateBookDTO dto = new UpdateBookDTO();
        dto.setTitle(bookForm.getTitle());
        dto.setAuthor(bookForm.getAuthor());
        dto.setIsbn(bookForm.getIsbn());
        dto.setPublishedYear(bookForm.getPublishedYear());
        bookService.updateBook(id, dto);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/copies/add")
    public String addCopy(@PathVariable Long id) {
        copyService.addCopy(id);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{bookId}/copies/{copyId}/toggle")
    public String toggleCopy(@PathVariable Long bookId, @PathVariable Long copyId) {
        BookCopyDTO copy = copyService.getCopiesByBookId(bookId).stream()
                .filter(c -> c.getId().equals(copyId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Copy not found"));
        copyService.updateAvailability(bookId, copyId, !copy.isAvailable());
        return "redirect:/books/" + bookId;
    }
}