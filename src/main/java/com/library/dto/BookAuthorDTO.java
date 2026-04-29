package com.library.dto;

/**
 * DTO returned by the custom JPQL inner-join query.
 * Carries both Book and Author fields into the view layer.
 */
public class BookAuthorDTO {

    private Long bookId;
    private String bookTitle;
    private String genre;
    private int publicationYear;
    private double price;
    private String isbn;

    private Long authorId;
    private String authorName;
    private String nationality;

    // ── Constructor used by JPQL "new" expression ─────────────
    public BookAuthorDTO(Long bookId, String bookTitle, String genre,
                         int publicationYear, double price, String isbn,
                         Long authorId, String authorName, String nationality) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.price = price;
        this.isbn = isbn;
        this.authorId = authorId;
        this.authorName = authorName;
        this.nationality = nationality;
    }

    // ── Getters ───────────────────────────────────────────────
    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getGenre() { return genre; }
    public int getPublicationYear() { return publicationYear; }
    public double getPrice() { return price; }
    public String getIsbn() { return isbn; }
    public Long getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
    public String getNationality() { return nationality; }

    // ── Setters ───────────────────────────────────────────────
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public void setPrice(double price) { this.price = price; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public void setNationality(String nationality) { this.nationality = nationality; }
}
