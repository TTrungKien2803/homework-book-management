package com.example.homework1.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookInventory implements Comparable<BookInventory>{
    private static int id = 0;
    private String bookId;
    private int amount;
    private LocalDateTime updateDate;

    public BookInventory( String bookId, int amount, LocalDateTime updateDate) {
        this.id++ ;
        this.bookId = bookId;
        this.amount = amount;
        this.updateDate = updateDate;
    }

    @Override
    public int compareTo(BookInventory bookInventory) {

        return this.getUpdateDate().compareTo(bookInventory.getUpdateDate());

    }
}
