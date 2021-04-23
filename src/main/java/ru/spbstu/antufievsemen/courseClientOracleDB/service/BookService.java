package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.BookRepository;

import java.util.List;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(long id) {
        return bookRepository.getOne(id);
    }

    public boolean deleteBookById(long id) {
        if (bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean addBook(Book book) {
        if (!bookRepository.existsBookBy(book)) {
            bookRepository.saveAndFlush(book);
            return true;
        }
        return false;
    }

    public boolean updateBook(Book book) {
        if (bookRepository.existsById(book.getId())) {
            bookRepository.saveAndFlush(book);
            return true;
        }
        return false;
    }
}
