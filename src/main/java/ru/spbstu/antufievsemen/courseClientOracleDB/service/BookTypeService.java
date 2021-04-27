package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.DeleteNullBookTypeException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.UpdateNullBookTypeException;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.BookTypeRepository;

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

    public BookType deleteBookType(long id) throws DeleteNullBookTypeException {
        Optional<BookType> bookTypeOptional = bookTypeRepository.findById(id);
        if (bookTypeOptional.isPresent()) {
            bookTypeRepository.deleteById(id);
            return bookTypeOptional.get();
        }
        throw new DeleteNullBookTypeException("Delete null book type ");
    }

    public BookType addBookType(BookType bookType) {
        return bookTypeRepository.saveAndFlush(bookType);
    }

    public BookType updateBooKType(BookType bookType) throws UpdateNullBookTypeException {
        Optional<BookType> optionalBookType = bookTypeRepository.findById(bookType.getId());
        if (optionalBookType.isPresent()) {
            return bookTypeRepository.saveAndFlush(bookType);
        }
        throw new UpdateNullBookTypeException("update null book type");
    }
}
