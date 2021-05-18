package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookTypeNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.BookTypeRepository;

@Service
@Transactional(rollbackOn = BookTypeNotFoundException.class)
public class BookTypeService {

    private final BookTypeRepository bookTypeRepository;

    public BookTypeService(BookTypeRepository bookTypeRepository) {
        this.bookTypeRepository = bookTypeRepository;
    }

    public List<BookType> getBookTypes() {
        return bookTypeRepository.findAll();
    }

    public Optional<BookType> getBookTypeById(long id) {
        return bookTypeRepository.findById(id);
    }

    public BookType deleteBookType(long id) throws BookTypeNotFoundException {
        Optional<BookType> bookTypeOptional = bookTypeRepository.findById(id);
        if (bookTypeOptional.isPresent()) {
            bookTypeRepository.deleteById(id);
            return bookTypeOptional.get();
        }
        throw new BookTypeNotFoundException("Delete null book type ");
    }

    public BookType addBookType(BookType bookType) {
        return bookTypeRepository.saveAndFlush(bookType);
    }

    public BookType updateBooKType(BookType bookType) throws BookTypeNotFoundException {
        Optional<BookType> optionalBookType = bookTypeRepository.findById(bookType.getId());
        if (optionalBookType.isPresent()) {
            return bookTypeRepository.saveAndFlush(bookType);
        }
        throw new BookTypeNotFoundException("update null book type");
    }
}
