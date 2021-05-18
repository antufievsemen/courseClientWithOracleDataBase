package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookTypeNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.BookRepository;

@Service
@Transactional(rollbackOn = BookNotFoundException.class)
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

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public Book deleteBookById(long id) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            if (getCountOfBook(id) > 0) {
                if (isAllBooksReturned(optionalBook.get().getRecords())) {
                    Book book = optionalBook.get();
                    BookType bookType = book.getBookType();
                    bookType.decrementOn(book.getCount());
                    bookTypeService.updateBooKType(bookType);
                    bookRepository.deleteById(id);
                    return book;
                }
            }
        }
        throw new BookNotFoundException("delete null book");
    }

    public Book addBook(Book book) {
        Optional<BookType> bookType = bookTypeService.getBookTypeById(book.getBookType().getId());
        if(bookType.isPresent()) {
            bookType.get().incrementOn(book.getCount());
            bookTypeService.updateBooKType(bookType.get());
            return bookRepository.saveAndFlush(book);
        }
        throw new BookTypeNotFoundException("type not found");
    }

    public Book updateBook(Book book) throws BookNotFoundException, BookTypeNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("update null book");
        }
        Optional<BookType> bookType = bookTypeService.getBookTypeById(book.getBookType().getId());
        if (bookType.isPresent()) {
            int temp_update = - optionalBook.get().getCount();
            bookType.get().decrementOn(temp_update);
            bookTypeService.updateBooKType(bookType.get());
            return bookRepository.saveAndFlush(book);
        }
        throw new BookTypeNotFoundException("book type not found");
    }

    public int getCountOfBook(long id) {
        return bookRepository.countOfBook(id);
    }

    private boolean isAllBooksReturned(Set<Record> recordSet) {
        for (Record record : recordSet) {
            if (record.getDateReturn() == null) {
                return false;
            }
        }
        return true;
    }
}
