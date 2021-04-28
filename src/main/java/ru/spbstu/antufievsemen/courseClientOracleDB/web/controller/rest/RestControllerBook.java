package ru.spbstu.antufievsemen.courseClientOracleDB.web.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.BookService;

@RestController
@RequestMapping("/library/")
public class RestControllerBook {

    private final BookService bookService;

    public RestControllerBook(BookService bookService) {
        this.bookService = bookService;
    }
}
