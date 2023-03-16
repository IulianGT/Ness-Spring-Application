package com.example.nesstest.Service;

import com.example.nesstest.Entity.Book;
import com.example.nesstest.Entity.Library;
import com.example.nesstest.exception.CustomException;
import com.example.nesstest.exception.CustomRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Library library = new Library();

    public static List<Book> getAllBooks() {

        if(library.getBookList().isEmpty())
            throw new CustomRequestException("The list of books is empty");

        return library.getBookList();
    }

    public static Book deleteBookById(int id) {
        Book deletedBook = new Book();
        if(library.getBookList().stream().noneMatch(book -> book.getBook_id() == id))
            throw new CustomRequestException("The book can not be found");

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

        if(library.getBookList().stream().noneMatch(book -> book.getBook_id() == id))
            throw new CustomRequestException("The book can not be found");

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

    public Book updateBook(int id, Book book){
        if(library.getBookList().stream().noneMatch(b -> b.getBook_id() == id))
            throw new CustomRequestException("The book can not be found");

        if(book.getAuthor().equals("") && book.getTitle().equals(""))
            throw new CustomRequestException("error at author and title input");

        if(library.getBookList().stream().anyMatch(b -> b.getBook_id() == id)){
                ArrayList<Book> newBookList = new ArrayList<>(library.getBookList().stream().peek(b -> {
                    if(b.getBook_id() == id){
                    b.setTitle(book.getTitle());
                    b.setAuthor(book.getAuthor());
                    }
                }).collect(Collectors.toList()));

                library.setBookList(newBookList);
        }
        return getBook(id);
    }


    public Book addBook(Book book){
        if(book.getAuthor().equals("") && book.getTitle().equals(""))
            throw new CustomRequestException("error at author and title input");

        if(library.getBookList() != null && library.getBookList().stream().anyMatch(book1 ->
                book1.getTitle().equals(book.getTitle())
                && book1.getAuthor().equals(book.getAuthor())))
            throw new CustomRequestException("This book already exists with the same author and title");

        library.addBookToList(book);
        return book;
    }

    public List<Book> showBooksSortedByAuthorAndTitle(){
        if(library.getBookList().isEmpty()){
            throw new CustomRequestException("Empty list.");
        }
        ArrayList<Book> sortedBooks =new ArrayList<>(library.getBookList().stream()
                .sorted(Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle))
                .collect(Collectors.toList()));

        library.setBookList(sortedBooks);
        return library.getBookList();
    }
}
