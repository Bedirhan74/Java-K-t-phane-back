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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.booktracker.model.Book;
import com.booktracker.service.BookService;

@Controller
@RequestMapping("/")
public class BookController {

    @Autowired
    private BookService bookService;
    
    // Ana sayfa - Tüm kitapları listele
    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("book", new Book()); // Yeni kitap formu için boş nesne
        return "index";
    }
    
    // Kitap kaydetme (ekleme veya güncelleme)
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
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
