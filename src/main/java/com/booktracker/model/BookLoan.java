package com.booktracker.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book_loans")
public class BookLoan {
    
    @Id
    private String id;
    
    private String bookId;
    private String bookTitle;
    private String borrowerName;
    private String borrowerSurname;
    private String borrowerPhone;
    private LocalDate loanDate;
    private LocalDate returnDate;
    
    public BookLoan(String bookId, String bookTitle, String borrowerName, String borrowerSurname, 
                    String borrowerPhone, LocalDate loanDate, LocalDate returnDate) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowerName = borrowerName;
        this.borrowerSurname = borrowerSurname;
        this.borrowerPhone = borrowerPhone;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }
    
    // Getirme tarihine kaç gün kaldığını hesaplayan metod
    public long getDaysUntilReturn() {
        if (returnDate == null) {
            return 0;
        }
        LocalDate today = LocalDate.now();
        return today.until(returnDate).getDays();
    }
    
    // Getirme tarihi geçmiş mi kontrolü
    public boolean isOverdue() {
        if (returnDate == null) {
            return false;
        }
        return LocalDate.now().isAfter(returnDate);
    }
}
