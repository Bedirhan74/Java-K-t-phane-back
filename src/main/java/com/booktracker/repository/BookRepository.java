package com.booktracker.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.booktracker.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    // Spring Data MongoDB otomatik olarak temel CRUD işlemlerini sağlar
    
    // Kitap adına göre arama (case-insensitive)
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Yazara göre arama (case-insensitive)
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    // Türe göre arama (case-insensitive)
    List<Book> findByGenreContainingIgnoreCase(String genre);
    
    // Yayın yılına göre arama
    List<Book> findByPublicationYear(int publicationYear);
    
    // Sayfa sayısı aralığına göre arama
    List<Book> findByPageCountBetween(int minPages, int maxPages);
    
    // Çoklu kritere göre arama
    @Query("{$and: [" +
           "?#{ [0] == null ? { $where: '1'} : { 'title': { $regex: [0], $options: 'i' } } }," +
           "?#{ [1] == null ? { $where: '1'} : { 'author': { $regex: [1], $options: 'i' } } }," +
           "?#{ [2] == null ? { $where: '1'} : { 'genre': { $regex: [2], $options: 'i' } } }," +
           "?#{ [3] == 0 ? { $where: '1'} : { 'publicationYear': [3] } }," +
           "?#{ [4] == 0 ? { $where: '1'} : { 'pageCount': { $gte: [4] } } }," +
           "?#{ [5] == 0 ? { $where: '1'} : { 'pageCount': { $lte: [5] } } }" +
           "]}")
    List<Book> findByFilters(String title, String author, String genre, int publicationYear, int minPages, int maxPages);
}
