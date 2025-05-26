package com.booktracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.booktracker.model.BookLoan;

@Repository
public interface BookLoanRepository extends MongoRepository<BookLoan, String> {
    
    // Kitap ID'sine göre ödünç verme kayıtlarını bul
    List<BookLoan> findByBookId(String bookId);
    
    // Belirli bir tarihten önce getirme tarihi olan ödünç verme kayıtlarını bul
    List<BookLoan> findByReturnDateBefore(LocalDate date);
    
    // Belirli bir tarih aralığında getirme tarihi olan ödünç verme kayıtlarını bul
    List<BookLoan> findByReturnDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Ödünç alanın adına göre arama
    List<BookLoan> findByBorrowerNameContainingIgnoreCase(String name);
    
    // Ödünç alanın soyadına göre arama
    List<BookLoan> findByBorrowerSurnameContainingIgnoreCase(String surname);
    
    // Ödünç alanın telefon numarasına göre arama
    List<BookLoan> findByBorrowerPhoneContaining(String phone);
    
    // Kitap adına göre arama
    List<BookLoan> findByBookTitleContainingIgnoreCase(String bookTitle);
    
    // Kitap adı ve ödünç alan kişi adına göre arama
    List<BookLoan> findByBookTitleContainingIgnoreCaseAndBorrowerNameContainingIgnoreCase(String bookTitle, String borrowerName);
}
