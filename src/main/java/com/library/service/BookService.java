package com.library.service;

import com.library.dto.BookAuthorDTO;
import com.library.exception.LibraryException;
import com.library.model.Author;
import com.library.model.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // ── CREATE ────────────────────────────────────────────────
    public Book saveBook(Book book, Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new LibraryException("Author not found with id: " + authorId));

        // Duplicate ISBN check
        if (book.getIsbn() != null && !book.getIsbn().isBlank()) {
            boolean exists = bookRepository.existsByIsbnAndIdNot(book.getIsbn(), -1L);
            if (exists) {
                throw new LibraryException("A book with ISBN '" + book.getIsbn() + "' already exists.");
            }
        }

        book.setAuthor(author);
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException ex) {
            throw new LibraryException("Failed to save book – data integrity violation: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }

    // ── READ ──────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        // Newest first (highest id first)
        return bookRepository.findAllOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Book not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorIdOrderByIdDesc(authorId);
    }

    @Transactional(readOnly = true)
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCaseOrderByIdDesc(title);
    }

    /**
     * Inner-join query: returns list of BookAuthorDTOs ordered by book id DESC
     * so that newly added books appear at the top of the joined view.
     */
    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getAllBooksWithAuthors() {
        return authorRepository.findAllBooksWithAuthors();
    }

    // ── UPDATE ────────────────────────────────────────────────
    public Book updateBook(Long id, Book updated, Long authorId) {
        Book existing = getBookById(id);

        // Duplicate ISBN check (excluding itself)
        if (updated.getIsbn() != null && !updated.getIsbn().isBlank()) {
            boolean exists = bookRepository.existsByIsbnAndIdNot(updated.getIsbn(), id);
            if (exists) {
                throw new LibraryException("Another book with ISBN '" + updated.getIsbn() + "' already exists.");
            }
        }

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new LibraryException("Author not found with id: " + authorId));

        existing.setTitle(updated.getTitle());
        existing.setGenre(updated.getGenre());
        existing.setPublicationYear(updated.getPublicationYear());
        existing.setPrice(updated.getPrice());
        existing.setIsbn(updated.getIsbn());
        existing.setAuthor(author);

        try {
            return bookRepository.save(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new LibraryException("Failed to update book: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }
}
