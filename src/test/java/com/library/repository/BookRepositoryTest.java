package com.library.repository;

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

@DataJpaTest
class BookRepositoryTest {

    @Autowired private BookRepository   bookRepository;
    @Autowired private AuthorRepository authorRepository;

    private Author author;
    private Book   book1, book2;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        author = authorRepository.save(new Author("J.K. Rowling", "British", "Harry Potter author."));
        book1  = bookRepository.save(new Book("Harry Potter 1", "Fantasy", 1997, 12.99, "ISBN-HP1", author));
        book2  = bookRepository.save(new Book("Harry Potter 2", "Fantasy", 1998, 13.99, "ISBN-HP2", author));
    }

    @Test
    @DisplayName("Save and find book by id")
    void testSaveAndFind() {
        Optional<Book> found = bookRepository.findById(book1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Harry Potter 1");
    }

    @Test
    @DisplayName("findAllOrderByIdDesc returns newest first")
    void testFindAllNewestFirst() {
        List<Book> books = bookRepository.findAllOrderByIdDesc();
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getId()).isGreaterThan(books.get(1).getId());
    }

    @Test
    @DisplayName("findByAuthorIdOrderByIdDesc filters correctly")
    void testFindByAuthorId() {
        List<Book> books = bookRepository.findByAuthorIdOrderByIdDesc(author.getId());
        assertThat(books).hasSize(2);
    }

    @Test
    @DisplayName("findByTitleContainingIgnoreCaseOrderByIdDesc works")
    void testSearchByTitle() {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrderByIdDesc("harry");
        assertThat(books).hasSize(2);
    }

    @Test
    @DisplayName("existsByIsbnAndIdNot detects duplicate ISBN")
    void testDuplicateIsbn() {
        // book2 already has ISBN-HP2; checking against a different id should return true
        boolean exists = bookRepository.existsByIsbnAndIdNot("ISBN-HP2", book1.getId());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("existsByIsbnAndIdNot returns false for same entity")
    void testNoDuplicateForSelf() {
        // Same id — should NOT flag itself
        boolean exists = bookRepository.existsByIsbnAndIdNot("ISBN-HP1", book1.getId());
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Update book title persists correctly")
    void testUpdateBook() {
        book1.setTitle("Updated Title");
        Book updated = bookRepository.save(book1);
        assertThat(updated.getTitle()).isEqualTo("Updated Title");
    }
}
