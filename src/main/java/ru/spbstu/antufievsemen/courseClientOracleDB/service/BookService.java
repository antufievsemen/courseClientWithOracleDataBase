package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.DeleteNullBookException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.UpdateNullBookException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.UpdateNullBookTypeException;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookTypeService bookTypeService;

    public BookService(BookRepository bookRepository, BookTypeService bookTypeService) {
        this.bookRepository = bookRepository;
        this.bookTypeService = bookTypeService;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(long id) {
        return bookRepository.getOne(id);
    }

    public Book deleteBookById(long id) throws DeleteNullBookException, UpdateNullBookTypeException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            if (getCountOfBook(id) > 0) {
                bookRepository.deleteById(id);
                Book book = optionalBook.get();
                BookType bookType = book.getBookType();
                bookType.decrementOn(book.getCount());
                bookTypeService.updateBooKType(bookType);
                return book;
            }
        }
        throw new DeleteNullBookException("delete null book");
    }

    public Book addBook(Book book) throws UpdateNullBookTypeException {
        book.getBookType().incrementOn(book.getCount());
        bookTypeService.updateBooKType(book.getBookType());
        return bookRepository.saveAndFlush(book);
    }

    public Book updateBook(Book book) throws UpdateNullBookException, DeleteNullBookException, UpdateNullBookTypeException {
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        if (optionalBook.isEmpty()) {
            throw new UpdateNullBookException("update null book");
        }
        BookType bookType = book.getBookType();
        int temp_update = optionalBook.get().getCount() - book.getCount();
        bookType.decrementOn(temp_update);
        bookTypeService.updateBooKType(bookType);
        return bookRepository.saveAndFlush(book);
    }

    public int getCountOfBook(long id) {
        return bookRepository.countOfBook(id);
    }
}
