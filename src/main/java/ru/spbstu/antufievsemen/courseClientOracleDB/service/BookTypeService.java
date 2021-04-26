package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.BookTypeRepository;

import java.util.List;

@Service
public class BookTypeService {

    private final BookTypeRepository bookTypeRepository;

    public BookTypeService(BookTypeRepository bookTypeRepository) {
        this.bookTypeRepository = bookTypeRepository;
    }

    public List<BookType> getBookTypes() {
        return bookTypeRepository.findAll();
    }

    public BookType getBookTypeById(long id) {
        return bookTypeRepository.getOne(id);
    }

    public boolean deleteBookType(long id) {
        if (bookTypeRepository.existsById(id)) {
            bookTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean addBookType(BookType bookType) {
        if (bookType == null || bookTypeRepository.existsBookTypeByNameAndFine(bookType.getName(), bookType.getFine())) {
            return false;
        }
        bookTypeRepository.saveAndFlush(bookType);
        return true;
    }

    public boolean updateBooKType(BookType bookType) {
        if (bookTypeRepository.existsById(bookType.getId())) {
            bookTypeRepository.saveAndFlush(bookType);
            return true;
        }
        return false;
    }

    public BookType getBookTypeByName(String name) {
        return bookTypeRepository.getBookTypeByName(name);
    }
}
