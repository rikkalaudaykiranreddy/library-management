package com.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Year;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Book title is required")
    @Size(max = 200, message = "Title must be at most 200 characters")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Genre is required")
    @Size(max = 100)
    @Column(length = 100)
    private String genre;

    @Min(value = 1000, message = "Publication year must be valid")
    @Column(name = "publication_year")
    private int publicationYear;

    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    private double price;

    @Size(max = 20)
    @Column(length = 20, unique = true)
    private String isbn;

    // Many-to-One → many books belong to one author
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // ── Constructors ─────────────────────────────────────────
    public Book() {}

    public Book(String title, String genre, int publicationYear, double price, String isbn, Author author) {
        this.title = title;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.price = price;
        this.isbn = isbn;
        this.author = author;
    }

    // ── Getters & Setters ─────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "'}";
    }
}
