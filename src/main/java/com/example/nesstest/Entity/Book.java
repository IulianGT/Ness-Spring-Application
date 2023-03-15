package com.example.nesstest.Entity;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Book {
    static int number_of_books=0;
    private int book_id;
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
