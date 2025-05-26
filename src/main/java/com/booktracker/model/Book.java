package com.booktracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {
    
    @Id
    private String id;
    
    private String title;
    private String author;
    private String isbn;
    private int pageCount;
    private String publisher;
    private int publicationYear;
    private String genre;
    private String description;
    
    // MongoDB otomatik ID oluşturması için constructor
    public Book(String title, String author, String isbn, int pageCount, 
                String publisher, int publicationYear, String genre, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.description = description;
    }
}
