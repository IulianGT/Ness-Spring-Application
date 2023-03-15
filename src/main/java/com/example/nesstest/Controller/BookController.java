package com.example.nesstest.Controller;

import com.example.nesstest.Entity.Book;
import com.example.nesstest.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping({"/books"})
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id){
        return bookService.getBook(id);
    }

    @DeleteMapping("/delete/{id}")
    public Book deleteBookById(@PathVariable int id) {
        return BookService.deleteBookById(id);
    }

    @PostMapping("/add")
    public Book createBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @GetMapping()
    public List<Book> getAllBooks(){
        return BookService.getAllBooks();
    }

    @GetMapping("/search_by_title={title}")
    public Book findBookByTitle(@PathVariable String title){
        return bookService.findBookByTitle(title);
    }

    @PutMapping("/updateBook/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book book){
        return bookService.updateBook(id,book);
    }
}
