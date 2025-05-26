package com.booktracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.booktracker.model.Book;
import com.booktracker.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    
    // Tüm kitapları getir
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    // ID'ye göre kitap getir
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }
    
    // Kitap kaydet (ekleme veya güncelleme)
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    // Kitap sil
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
    
    // Kitap adına göre arama
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    // Yazara göre arama
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
    
    // Türe göre arama
    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenreContainingIgnoreCase(genre);
    }
    
    // Yayın yılına göre arama
    public List<Book> findByPublicationYear(int publicationYear) {
        return bookRepository.findByPublicationYear(publicationYear);
    }
    
    // Sayfa sayısı aralığına göre arama
    public List<Book> findByPageCountRange(int minPages, int maxPages) {
        return bookRepository.findByPageCountBetween(minPages, maxPages);
    }
    
    // Filtreleme kriterleri ile kitapları getir
    public List<Book> findBooksByFilters(String title, String author, String genre, 
                                        Integer publicationYear, Integer minPages, Integer maxPages) {
        // Boş değerler için null veya 0 atama
        String titleFilter = StringUtils.hasText(title) ? title : null;
        String authorFilter = StringUtils.hasText(author) ? author : null;
        String genreFilter = StringUtils.hasText(genre) ? genre : null;
        int yearFilter = (publicationYear != null && publicationYear > 0) ? publicationYear : 0;
        int minPagesFilter = (minPages != null && minPages > 0) ? minPages : 0;
        int maxPagesFilter = (maxPages != null && maxPages > 0) ? maxPages : 0;
        
        // Hiçbir filtre seçilmemişse tüm kitapları getir
        if (titleFilter == null && authorFilter == null && genreFilter == null && 
            yearFilter == 0 && minPagesFilter == 0 && maxPagesFilter == 0) {
            return getAllBooks();
        }
        
        return bookRepository.findByFilters(titleFilter, authorFilter, genreFilter, 
                                          yearFilter, minPagesFilter, maxPagesFilter);
    }
}
