package com.library.repository;

import com.library.dto.BookAuthorDTO;
import com.library.model.Author;
import com.library.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository-layer integration tests using @DataJpaTest (in-memory H2).
 */
@DataJpaTest
class AuthorRepositoryTest {

    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookRepository   bookRepository;

    private Author savedAuthor;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        savedAuthor = authorRepository.save(new Author("Test Author", "Indian", "A test bio."));
    }

    // ── CRUD ───────────────────────────────────────────────────
    @Test
    @DisplayName("Save and find author by id")
    void testSaveAndFindById() {
        Optional<Author> found = authorRepository.findById(savedAuthor.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Author");
    }

    @Test
    @DisplayName("Update author name")
    void testUpdateAuthor() {
        savedAuthor.setName("Updated Author");
        Author updated = authorRepository.save(savedAuthor);
        assertThat(updated.getName()).isEqualTo("Updated Author");
    }

    @Test
    @DisplayName("Find authors by nationality (case-insensitive)")
    void testFindByNationality() {
        List<Author> result = authorRepository.findByNationalityIgnoreCase("indian");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Author");
    }

    @Test
    @DisplayName("Find authors by partial name (case-insensitive)")
    void testFindByNameContaining() {
        List<Author> result = authorRepository.findByNameContainingIgnoreCase("test");
        assertThat(result).hasSize(1);
    }

    // ── Inner Join Query ────────────────────────────────────────
    @Test
    @DisplayName("Inner join returns correct BookAuthorDTOs ordered newest first")
    void testFindAllBooksWithAuthors() {
        Book b1 = bookRepository.save(new Book("Book One", "Fiction", 2001, 9.99, "ISBN-001", savedAuthor));
        Book b2 = bookRepository.save(new Book("Book Two", "Drama",   2010, 12.0, "ISBN-002", savedAuthor));

        List<BookAuthorDTO> result = authorRepository.findAllBooksWithAuthors();

        assertThat(result).hasSize(2);
        // Newest (highest id) should be first
        assertThat(result.get(0).getBookId()).isGreaterThan(result.get(1).getBookId());
        assertThat(result.get(0).getAuthorName()).isEqualTo("Test Author");
    }

    @Test
    @DisplayName("Inner join returns empty list when no books exist")
    void testInnerJoinEmptyWhenNoBooks() {
        List<BookAuthorDTO> result = authorRepository.findAllBooksWithAuthors();
        assertThat(result).isEmpty();
    }
}
