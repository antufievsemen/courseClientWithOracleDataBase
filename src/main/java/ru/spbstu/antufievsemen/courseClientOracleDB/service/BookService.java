package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import ru.spbstu.antufievsemen.courseClientOracleDB.repository.BookRepository;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
