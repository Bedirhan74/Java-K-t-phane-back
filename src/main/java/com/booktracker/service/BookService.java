package com.booktracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booktracker.model.Book;
import com.booktracker.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    
    // Tu00fcm kitaplaru0131 getir
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    // ID'ye gu00f6re kitap getir
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }
    
    // Kitap kaydet (ekleme veya gu00fcncelleme)
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    // Kitap sil
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
