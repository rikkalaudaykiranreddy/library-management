package com.library.service;

import com.library.exception.LibraryException;
import com.library.model.Author;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock    private AuthorRepository authorRepository;
    @InjectMocks private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("Leo Tolstoy", "Russian", "Classic novelist.");
        author.setId(1L);
    }

    // ── saveAuthor ────────────────────────────────────────────
    @Test
    @DisplayName("saveAuthor — saves and returns author")
    void saveAuthor_success() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        Author result = authorService.saveAuthor(author);
        assertThat(result.getName()).isEqualTo("Leo Tolstoy");
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    @DisplayName("saveAuthor — wraps DataIntegrityViolationException")
    void saveAuthor_integrityViolation() {
        when(authorRepository.save(any(Author.class)))
                .thenThrow(new DataIntegrityViolationException("constraint"));
        assertThatThrownBy(() -> authorService.saveAuthor(author))
                .isInstanceOf(LibraryException.class)
                .hasMessageContaining("data integrity");
    }

    // ── getAllAuthors ──────────────────────────────────────────
    @Test
    @DisplayName("getAllAuthors — returns sorted list newest first")
    void getAllAuthors_sortedNewestFirst() {
        Author a1 = new Author("Author A", "X", ""); a1.setId(1L);
        Author a2 = new Author("Author B", "Y", ""); a2.setId(2L);
        when(authorRepository.findAll()).thenReturn(List.of(a1, a2));

        List<Author> result = authorService.getAllAuthors();

        assertThat(result.get(0).getId()).isEqualTo(2L); // highest id first
        assertThat(result.get(1).getId()).isEqualTo(1L);
    }

    // ── getAuthorById ─────────────────────────────────────────
    @Test
    @DisplayName("getAuthorById — returns author when found")
    void getAuthorById_found() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Author result = authorService.getAuthorById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getAuthorById — throws LibraryException when not found")
    void getAuthorById_notFound() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.getAuthorById(99L))
                .isInstanceOf(LibraryException.class)
                .hasMessageContaining("Author not found");
    }

    // ── updateAuthor ──────────────────────────────────────────
    @Test
    @DisplayName("updateAuthor — updates fields and saves")
    void updateAuthor_success() {
        Author updated = new Author("Updated Name", "French", "New bio.");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));

        Author result = authorService.updateAuthor(1L, updated);

        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getNationality()).isEqualTo("French");
        verify(authorRepository).save(author);
    }

    @Test
    @DisplayName("updateAuthor — throws when author not found")
    void updateAuthor_notFound() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.updateAuthor(99L, author))
                .isInstanceOf(LibraryException.class);
    }
}
