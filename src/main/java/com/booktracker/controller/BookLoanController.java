package com.booktracker.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.booktracker.model.Book;
import com.booktracker.model.BookLoan;
import com.booktracker.service.BookLoanService;
import com.booktracker.service.BookService;

@Controller
@RequestMapping("/loans")
public class BookLoanController {

    @Autowired
    private BookLoanService bookLoanService;
    
    @Autowired
    private BookService bookService;
    
    // Kitap ödünç verme işlemi
    @PostMapping("/borrow")
    public String borrowBook(
            @RequestParam("bookId") String bookId,
            @RequestParam("borrowerName") String borrowerName,
            @RequestParam("borrowerSurname") String borrowerSurname,
            @RequestParam("borrowerPhone") String borrowerPhone,
            @RequestParam("returnDate") String returnDate,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Kitap bilgilerini al
            Book book = bookService.getBookById(bookId)
                    .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));
            
            // Yeni ödünç verme kaydı oluştur
            BookLoan bookLoan = new BookLoan(
                    bookId,
                    book.getTitle(),
                    borrowerName,
                    borrowerSurname,
                    borrowerPhone,
                    LocalDate.now(),
                    LocalDate.parse(returnDate)
            );
            
            // Ödünç verme kaydını kaydet
            bookLoanService.saveBookLoan(bookLoan);
            
            redirectAttributes.addFlashAttribute("success", "Kitap başarıyla ödünç verildi.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Kitap ödünç verme işlemi sırasında bir hata oluştu: " + e.getMessage());
        }
        
        return "redirect:/";
    }
    
    // Ödünç verme kaydını silme işlemi
    @PostMapping("/return")
    public String returnBook(
            @RequestParam("loanId") String loanId,
            RedirectAttributes redirectAttributes) {
        
        try {
            bookLoanService.deleteBookLoan(loanId);
            redirectAttributes.addFlashAttribute("success", "Kitap iade işlemi başarıyla tamamlandı.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Kitap iade işlemi sırasında bir hata oluştu: " + e.getMessage());
        }
        
        return "redirect:/";
    }
}
