package com.library.controller;

import com.library.exception.LibraryException;
import com.library.model.Book;
import com.library.service.AuthorService;
import com.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // ── LIST (newest first) ───────────────────────────────────
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("pageTitle", "Books");
        return "books/list";
    }

    // ── JOINED VIEW ───────────────────────────────────────────
    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("catalog", bookService.getAllBooksWithAuthors());
        model.addAttribute("pageTitle", "Book Catalog");
        return "books/catalog";
    }

    // ── ADD FORM ──────────────────────────────────────────────
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("pageTitle", "Add Book");
        return "books/form";
    }

    // ── SAVE (CREATE) ─────────────────────────────────────────
    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult bindingResult,
                           @RequestParam("authorId") Long authorId,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("pageTitle", "Add Book");
            return "books/form";
        }
        try {
            bookService.saveBook(book, authorId);
            redirectAttributes.addFlashAttribute("successMsg", "Book added successfully!");
        } catch (LibraryException ex) {
            redirectAttributes.addFlashAttribute("errorMsg", ex.getMessage());
        }
        return "redirect:/books";
    }

    // ── EDIT FORM ─────────────────────────────────────────────
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.getBookById(id);
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("selectedAuthorId", book.getAuthor().getId());
            model.addAttribute("pageTitle", "Edit Book");
            return "books/form";
        } catch (LibraryException ex) {
            redirectAttributes.addFlashAttribute("errorMsg", ex.getMessage());
            return "redirect:/books";
        }
    }

    // ── UPDATE ────────────────────────────────────────────────
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult,
                             @RequestParam("authorId") Long authorId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("selectedAuthorId", authorId);
            model.addAttribute("pageTitle", "Edit Book");
            return "books/form";
        }
        try {
            bookService.updateBook(id, book, authorId);
            redirectAttributes.addFlashAttribute("successMsg", "Book updated successfully!");
        } catch (LibraryException ex) {
            redirectAttributes.addFlashAttribute("errorMsg", ex.getMessage());
        }
        return "redirect:/books";
    }
}
