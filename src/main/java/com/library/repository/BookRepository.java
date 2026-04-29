package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /** All books for a specific author, newest first. */
    List<Book> findByAuthorIdOrderByIdDesc(Long authorId);

    /** Search books by title (case-insensitive). */
    List<Book> findByTitleContainingIgnoreCaseOrderByIdDesc(String title);

    /** Search books by genre (case-insensitive). */
    List<Book> findByGenreIgnoreCaseOrderByIdDesc(String genre);

    /** Custom query: all books ordered newest first. */
    @Query("SELECT b FROM Book b ORDER BY b.id DESC")
    List<Book> findAllOrderByIdDesc();

    /** Check duplicate ISBN (excluding the book being updated). */
    @Query("SELECT COUNT(b) > 0 FROM Book b WHERE b.isbn = :isbn AND b.id <> :id")
    boolean existsByIsbnAndIdNot(@Param("isbn") String isbn, @Param("id") Long id);
}
