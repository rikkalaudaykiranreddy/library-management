package com.library.service;

import com.library.exception.LibraryException;
import com.library.model.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // ── CREATE ────────────────────────────────────────────────
    public Author saveAuthor(Author author) {
        try {
            return authorRepository.save(author);
        } catch (DataIntegrityViolationException ex) {
            throw new LibraryException("Failed to save author – data integrity violation: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }

    // ── READ ──────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        // Ordered by id DESC so the newest author appears at the top
        List<Author> authors = new java.util.ArrayList<>(authorRepository.findAll());
        authors.sort((a, b) -> Long.compare(b.getId(), a.getId()));
        return authors;
    }

    @Transactional(readOnly = true)
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Author not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Author> searchByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    // ── UPDATE ────────────────────────────────────────────────
    public Author updateAuthor(Long id, Author updated) {
        Author existing = getAuthorById(id);
        existing.setName(updated.getName());
        existing.setNationality(updated.getNationality());
        existing.setBio(updated.getBio());
        try {
            return authorRepository.save(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new LibraryException("Failed to update author: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }
}
