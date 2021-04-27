package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookLimitException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.DeleteNullBookException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.UpdateNullBookException;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final RecordService recordService;

    public BookService(BookRepository bookRepository, RecordService recordService) {
        this.bookRepository = bookRepository;
        this.recordService = recordService;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(long id) {
        return bookRepository.getOne(id);
    }

    public Book deleteBookById(long id) throws DeleteNullBookException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            if (recordService.existBook(optionalBook.get())) {
                bookRepository.deleteById(id);
                Book book = optionalBook.get();
                book.getBookType().decrementCount();
                return book;
            }
        }
        throw new DeleteNullBookException("delete null book");
    }

    public Book addBook(Book book) throws BookLimitException {
        book.getBookType().incrementCount();
        return bookRepository.saveAndFlush(book);
    }

    public Book updateBook(Book book) throws UpdateNullBookException {
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        if (optionalBook.isPresent()) {
            if (recordService.existBook(optionalBook.get())) {
                return bookRepository.saveAndFlush(book);
            }
        }
        throw new UpdateNullBookException("update null book");
    }
}
