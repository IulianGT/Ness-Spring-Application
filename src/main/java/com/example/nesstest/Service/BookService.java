package com.example.nesstest.Service;

import com.example.nesstest.Entity.Book;
import com.example.nesstest.Entity.Library;
import com.example.nesstest.exception.CustomException;
import com.example.nesstest.exception.CustomRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Library library = new Library();

    public static List<Book> getAllBooks() {
        return library.getBookList();
    }

    public static Book deleteBookById(int id) {
        Book deletedBook = new Book();
        library.getBookList().forEach(book -> {
            if(book.getBook_id() == id){
                deletedBook.setBook_id(book.getBook_id());
                deletedBook.setAuthor(book.getAuthor());
                deletedBook.setTitle(book.getTitle());
            }
        });
        ArrayList<Book> newBookList = library.getBookList().stream().filter(book -> book.getBook_id() != id).collect(Collectors.toCollection(ArrayList::new));
        library.setBookList(newBookList);

        return deletedBook;
    }

    public Book getBook(int id) {
        for(Book book: library.getBookList()){
            if(book.getBook_id() == id ){
                return book;
            }
        }
        throw new CustomRequestException("the book doesn't exist!");
    }

    public Book findBookByTitle(String searchedTitle) {
        Optional<Book> foundBook = library.getBookList().stream()
                .filter(book -> book.getTitle().equals(searchedTitle)).findFirst();

        if(foundBook.isPresent())
            return foundBook.get();
        else{
            throw new CustomRequestException("cannot find the specified title");
        }
    }


    public Book addBook(Book book){
        if(book.getAuthor().equals("") && book.getTitle().equals(""))
            throw new CustomRequestException("error at author and title input");

        library.addBookToList(book);
        return book;

    }
}
