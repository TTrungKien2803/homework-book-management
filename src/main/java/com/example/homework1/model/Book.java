package com.example.homework1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Comparable<Book> {
    private String id;
    private String title;
    private String author;

    @Override
    public int compareTo(Book book) {
        return this.getTitle().compareTo(book.getTitle());
    }
}
