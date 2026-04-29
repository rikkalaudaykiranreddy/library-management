package com.library.service;

import com.library.dto.BookAuthorDTO;
import com.library.exception.LibraryException;
import com.library.model.Author;
import com.library.model.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock    private BookRepository   bookRepository;
    @Mock    private AuthorRepository authorRepository;
    @InjectMocks private BookService  bookService;

    private Author author;
    private Book   book;

    @BeforeEach
    void setUp() {
        author = new Author("Jane Austen", "British", "Pride & Prejudice author.");
        author.setId(1L);

        book = new Book("Pride and Prejudice", "Romance", 1813, 8.99, "ISBN-PP", author);
        book.setId(1L);
    }

    // ── saveBook ──────────────────────────────────────────────
    @Test
    @DisplayName("saveBook — saves when author exists and ISBN is unique")
    void saveBook_success() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.existsByIsbnAndIdNot("ISBN-PP", -1L)).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.saveBook(book, 1L);
        assertThat(result.getTitle()).isEqualTo("Pride and Prejudice");
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("saveBook — throws when author not found")
    void saveBook_authorNotFound() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.saveBook(book, 99L))
                .isInstanceOf(LibraryException.class)
                .hasMessageContaining("Author not found");
    }

    @Test
    @DisplayName("saveBook — throws when duplicate ISBN")
    void saveBook_duplicateIsbn() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.existsByIsbnAndIdNot("ISBN-PP", -1L)).thenReturn(true);

        assertThatThrownBy(() -> bookService.saveBook(book, 1L))
                .isInstanceOf(LibraryException.class)
                .hasMessageContaining("already exists");
    }

    // ── getAllBooks ───────────────────────────────────────────
    @Test
    @DisplayName("getAllBooks — returns list from repository")
    void getAllBooks_returnsList() {
        when(bookRepository.findAllOrderByIdDesc()).thenReturn(List.of(book));
        List<Book> result = bookService.getAllBooks();
        assertThat(result).hasSize(1);
        verify(bookRepository).findAllOrderByIdDesc();
    }

    // ── getBookById ───────────────────────────────────────────
    @Test
    @DisplayName("getBookById — returns book when found")
    void getBookById_found() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertThat(bookService.getBookById(1L).getTitle()).isEqualTo("Pride and Prejudice");
    }

    @Test
    @DisplayName("getBookById — throws LibraryException when not found")
    void getBookById_notFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getBookById(99L))
                .isInstanceOf(LibraryException.class)
                .hasMessageContaining("Book not found");
    }

    // ── getAllBooksWithAuthors ─────────────────────────────────
    @Test
    @DisplayName("getAllBooksWithAuthors — delegates to authorRepository")
    void getAllBooksWithAuthors_delegates() {
        BookAuthorDTO dto = new BookAuthorDTO(1L,"Title","Genre",2000,9.0,"ISBN",1L,"Name","British");
        when(authorRepository.findAllBooksWithAuthors()).thenReturn(List.of(dto));

        List<BookAuthorDTO> result = bookService.getAllBooksWithAuthors();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAuthorName()).isEqualTo("Name");
    }

    // ── updateBook ────────────────────────────────────────────
    @Test
    @DisplayName("updateBook — updates all fields and saves")
    void updateBook_success() {
        Book updated = new Book("Sense and Sensibility","Classic",1811,7.50,"ISBN-SS",null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.existsByIsbnAndIdNot("ISBN-SS", 1L)).thenReturn(false);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.updateBook(1L, updated, 1L);
        assertThat(result.getTitle()).isEqualTo("Sense and Sensibility");
        assertThat(result.getIsbn()).isEqualTo("ISBN-SS");
    }

    @Test
    @DisplayName("updateBook — throws on duplicate ISBN for different book")
    void updateBook_duplicateIsbn() {
        Book updated = new Book("Other","Fiction",2000,5.0,"ISBN-OTHER",null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.existsByIsbnAndIdNot("ISBN-OTHER", 1L)).thenReturn(true);

        assertThatThrownBy(() -> bookService.updateBook(1L, updated, 1L))
                .isInstanceOf(LibraryException.class)
                .hasMessageContaining("Another book");
    }
}
