package com.example.nesstest.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Library {
    static int number_of_books_added = 0;
    private ArrayList<Book> bookList;

    public Book addBookToList(Book book){
        number_of_books_added++;
        book.setBook_id(number_of_books_added);

        if(bookList == null){
            this.bookList = new ArrayList<>(List.of(book));
        }
        else{
        bookList.add(book);
        }

        return book;
    }
}
