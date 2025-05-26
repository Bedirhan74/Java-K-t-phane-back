package com.booktracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.booktracker.model.Book;
import com.booktracker.model.BookLoan;
import com.booktracker.service.BookLoanService;
import com.booktracker.service.BookService;

@Controller
@RequestMapping("/")
public class BookController {

    @Autowired
    private BookService bookService;
    
    @Autowired
    private BookLoanService bookLoanService;
    
    // REST API endpoint'leri BookRestController sınıfına taşındı
    
    // Ana sayfa - Tüm kitapları listele (filtreleme seçenekleriyle)
    @GetMapping
    public String listBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer publicationYear,
            @RequestParam(required = false) Integer minPages,
            @RequestParam(required = false) Integer maxPages,
            Model model) {
        
        // Filtreleme parametrelerini modele ekle (form değerlerini korumak için)
        model.addAttribute("filterTitle", title);
        model.addAttribute("filterAuthor", author);
        model.addAttribute("filterGenre", genre);
        model.addAttribute("filterPublicationYear", publicationYear);
        model.addAttribute("filterMinPages", minPages);
        model.addAttribute("filterMaxPages", maxPages);
        
        // Filtreleme işlemi
        List<Book> books;
        if (title != null || author != null || genre != null || 
            publicationYear != null || minPages != null || maxPages != null) {
            // En az bir filtre parametresi varsa filtreleme yap
            books = bookService.findBooksByFilters(title, author, genre, publicationYear, minPages, maxPages);
            model.addAttribute("filterActive", true);
        } else {
            // Filtre yoksa tüm kitapları getir
            books = bookService.getAllBooks();
            model.addAttribute("filterActive", false);
        }
        
        // Ödünç verilen kitapların listesini al
        List<BookLoan> bookLoans = bookLoanService.getAllBookLoans();
        
        model.addAttribute("books", books);
        model.addAttribute("book", new Book()); // Yeni kitap formu için boş nesne
        model.addAttribute("bookLoans", bookLoans); // Ödünç verilen kitaplar listesi
        return "index";
    }
    
    // Filtreleri temizle
    @GetMapping("/clear-filters")
    public String clearFilters() {
        return "redirect:/";
    }
    
    // Kitap kaydetme (ekleme veya güncelleme)
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        // Eğer id boş string ise null olarak ayarla (yeni kayıt için)
        if (book.getId() != null && book.getId().trim().isEmpty()) {
            book.setId(null);
        }
        
        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute("success", 
            book.getId() == null ? "Kitap başarıyla eklendi." : "Kitap başarıyla güncellendi.");
        return "redirect:/";
    }
    
    // Kitap düzenleme formunu göster
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        bookService.getBookById(id).ifPresent(book -> model.addAttribute("book", book));
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("editMode", true);
        return "index";
    }
    
    // Kitap silme
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id, RedirectAttributes redirectAttributes) {
        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("success", "Kitap başarıyla silindi.");
        return "redirect:/";
    }
}
