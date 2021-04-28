package ru.spbstu.antufievsemen.courseClientOracleDB.web.controller.rest;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.BookService;

@RestController
@RequestMapping("/library/books")
public class RestControllerBook {

    private final BookService bookService;

    public RestControllerBook(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getList() {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public Book getOne(@PathVariable long id) {
        return bookService.getBookById(id).
                orElseGet(() -> {throw new BookNotFoundException("book not found");});
    }

    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/updateBook/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable long id) {
        book.setId(id);
        return bookService.updateBook(book);
    }

    @DeleteMapping("/deleteBook/{id}")
    public Book deleteBook(@PathVariable long id) {
        return bookService.deleteBookById(id);
    }
}
