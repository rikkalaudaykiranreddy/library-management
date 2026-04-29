package com.library;

import com.library.model.Author;
import com.library.model.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(AuthorRepository authorRepo, BookRepository bookRepo) {
        return args -> {
            if (authorRepo.count() > 0) return; // already seeded

            // ── 10 Authors ────────────────────────────────────
            Author a1  = authorRepo.save(new Author("George Orwell",        "British",   "English novelist, known for dystopian fiction."));
            Author a2  = authorRepo.save(new Author("J.K. Rowling",         "British",   "Author of the Harry Potter fantasy series."));
            Author a3  = authorRepo.save(new Author("Fyodor Dostoevsky",    "Russian",   "Renowned for psychological insight in fiction."));
            Author a4  = authorRepo.save(new Author("Agatha Christie",      "British",   "Queen of mystery, world's best-selling fiction writer."));
            Author a5  = authorRepo.save(new Author("Gabriel García Márquez","Colombian","Master of magical realism literature."));
            Author a6  = authorRepo.save(new Author("Mark Twain",           "American",  "Famed humorist and social critic."));
            Author a7  = authorRepo.save(new Author("Leo Tolstoy",          "Russian",   "Author of epic novels and moral tales."));
            Author a8  = authorRepo.save(new Author("Toni Morrison",        "American",  "Nobel laureate known for African-American literature."));
            Author a9  = authorRepo.save(new Author("Haruki Murakami",      "Japanese",  "Surrealist and postmodern fiction writer."));
            Author a10 = authorRepo.save(new Author("Jane Austen",          "British",   "Pioneer of the modern novel."));

            // ── 10 Books ──────────────────────────────────────
            bookRepo.save(new Book("1984",                      "Dystopian",       1949, 9.99,  "978-0451524935", a1));
            bookRepo.save(new Book("Animal Farm",               "Political Satire",1945, 7.49,  "978-0451526342", a1));
            bookRepo.save(new Book("Harry Potter & Sorcerer's Stone","Fantasy",   1997, 12.99, "978-0439708180", a2));
            bookRepo.save(new Book("Crime and Punishment",      "Psychological",   1866, 8.99,  "978-0143058144", a3));
            bookRepo.save(new Book("Murder on the Orient Express","Mystery",      1934, 10.49, "978-0062693662", a4));
            bookRepo.save(new Book("One Hundred Years of Solitude","Magical Realism",1967,11.99,"978-0060883287",a5));
            bookRepo.save(new Book("Adventures of Huckleberry Finn","Adventure", 1884, 6.99,  "978-0486280615", a6));
            bookRepo.save(new Book("War and Peace",             "Historical",      1869, 14.99, "978-1400079988", a7));
            bookRepo.save(new Book("Beloved",                   "Historical",      1987, 9.49,  "978-1400033416", a8));
            bookRepo.save(new Book("Norwegian Wood",            "Romance",         1987, 10.99, "978-0375704024", a9));
        };
    }
}
