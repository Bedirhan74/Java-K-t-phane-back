package com.booktracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booktracker.model.BookLoan;
import com.booktracker.repository.BookLoanRepository;

@Service
public class BookLoanService {

    @Autowired
    private BookLoanRepository bookLoanRepository;
    
    // Tüm ödünç verme kayıtlarını getir
    public List<BookLoan> getAllBookLoans() {
        return bookLoanRepository.findAll();
    }
    
    // ID'ye göre ödünç verme kaydı getir
    public Optional<BookLoan> getBookLoanById(String id) {
        return bookLoanRepository.findById(id);
    }
    
    // Kitap ID'sine göre ödünç verme kayıtlarını getir
    public List<BookLoan> getBookLoansByBookId(String bookId) {
        return bookLoanRepository.findByBookId(bookId);
    }
    
    // Ödünç verme kaydı kaydet (ekleme veya güncelleme)
    public BookLoan saveBookLoan(BookLoan bookLoan) {
        return bookLoanRepository.save(bookLoan);
    }
    
    // Ödünç verme kaydı sil
    public void deleteBookLoan(String id) {
        bookLoanRepository.deleteById(id);
    }
    
    // Gecikmiş ödünç verme kayıtlarını getir
    public List<BookLoan> getOverdueBookLoans() {
        return bookLoanRepository.findByReturnDateBefore(LocalDate.now());
    }
    
    // Yaklaşan getirme tarihli ödünç verme kayıtlarını getir (7 gün veya daha az)
    public List<BookLoan> getUpcomingReturnBookLoans() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysLater = today.plusDays(7);
        return bookLoanRepository.findByReturnDateBetween(today, sevenDaysLater);
    }
}
