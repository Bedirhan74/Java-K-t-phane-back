package com.booktracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.booktracker.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    // Spring Data MongoDB otomatik olarak temel CRUD işlemlerini sağlar
    // İhtiyaç duyulursa özel sorgular burada tanımlanabilir
}
