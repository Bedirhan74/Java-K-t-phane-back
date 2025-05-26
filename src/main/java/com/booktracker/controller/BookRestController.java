package com.booktracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booktracker.model.Book;
import com.booktracker.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Kitap", description = "Kitap yönetimi API'si")
public class BookRestController {

    @Autowired
    private BookService bookService;
    
    @Operation(summary = "Tüm kitapları listele", description = "Veritabanındaki tüm kitapları listeler")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Kitaplar başarıyla listelendi",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    })
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    
    @Operation(summary = "Kitap detaylarını getir", description = "ID'ye göre kitap detaylarını getirir")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Kitap bulundu",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
        @ApiResponse(responseCode = "404", description = "Kitap bulunamadı", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "Kitap ID", required = true) @PathVariable String id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Yeni kitap ekle", description = "Veritabanına yeni bir kitap ekler")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Kitap başarıyla eklendi",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    })
    @PostMapping
    public ResponseEntity<Book> createBook(
            @Parameter(description = "Eklenecek kitap", required = true, 
                    schema = @Schema(implementation = Book.class)) 
            @RequestBody Book book) {
        // ID'yi null yap ki MongoDB otomatik oluştursun
        book.setId(null);
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }
    
    @Operation(summary = "Kitap güncelle", description = "Mevcut bir kitabı günceller")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Kitap başarıyla güncellendi",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
        @ApiResponse(responseCode = "404", description = "Güncellenecek kitap bulunamadı", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "Güncellenecek kitabın ID'si", required = true) 
            @PathVariable String id,
            @Parameter(description = "Güncellenmiş kitap bilgileri", required = true, 
                    schema = @Schema(implementation = Book.class)) 
            @RequestBody Book book) {
        
        return bookService.getBookById(id)
                .map(existingBook -> {
                    book.setId(id); // ID'yi koru
                    Book updatedBook = bookService.saveBook(book);
                    return ResponseEntity.ok(updatedBook);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Kitap sil", description = "ID'ye göre kitap siler")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Kitap başarıyla silindi", content = @Content),
        @ApiResponse(responseCode = "404", description = "Silinecek kitap bulunamadı", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "Silinecek kitabın ID'si", required = true) 
            @PathVariable String id) {
        
        return bookService.getBookById(id)
                .map(book -> {
                    bookService.deleteBook(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Filtreleme ile kitapları listele", 
            description = "Çeşitli kriterlere göre kitapları filtreler")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Kitaplar başarıyla filtrelendi",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    })
    @GetMapping("/filter")
    public ResponseEntity<List<Book>> filterBooks(
            @Parameter(description = "Kitap adı") @RequestParam(required = false) String title,
            @Parameter(description = "Yazar") @RequestParam(required = false) String author,
            @Parameter(description = "Tür") @RequestParam(required = false) String genre,
            @Parameter(description = "Yayın yılı") @RequestParam(required = false) Integer publicationYear,
            @Parameter(description = "Minimum sayfa sayısı") @RequestParam(required = false) Integer minPages,
            @Parameter(description = "Maksimum sayfa sayısı") @RequestParam(required = false) Integer maxPages) {
        
        List<Book> filteredBooks = bookService.findBooksByFilters(
                title, author, genre, publicationYear, minPages, maxPages);
        
        return ResponseEntity.ok(filteredBooks);
    }
}
