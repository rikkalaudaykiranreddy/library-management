package com.library.repository;

import com.library.dto.BookAuthorDTO;
import com.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /** Find authors by nationality (case-insensitive). */
    List<Author> findByNationalityIgnoreCase(String nationality);

    /** Find authors whose name contains the given string (case-insensitive). */
    List<Author> findByNameContainingIgnoreCase(String name);

    /**
     * Custom JPQL inner join – returns combined Book+Author data.
     * Results ordered by bookId DESC so newest entries appear first.
     */
    @Query("SELECT new com.library.dto.BookAuthorDTO(" +
           "b.id, b.title, b.genre, b.publicationYear, b.price, b.isbn, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a " +
           "ORDER BY b.id DESC")
    List<BookAuthorDTO> findAllBooksWithAuthors();
}
