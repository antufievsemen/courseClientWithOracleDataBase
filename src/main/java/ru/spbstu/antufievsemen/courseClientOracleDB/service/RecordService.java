package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookLimitException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.RecordNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.RecordRepository;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final BookTypeService bookTypeService;
    private final BookService bookService;

    public RecordService(RecordRepository recordRepository, BookTypeService bookTypeService, BookService bookService) {
        this.recordRepository = recordRepository;
        this.bookTypeService = bookTypeService;
        this.bookService = bookService;
    }

    public List<Record> getRecords() {
        return recordRepository.findAll();
    }

    public Optional<Record> getRecordById(long id) {
        return recordRepository.findById(id);
    }

    public Record deleteRecord(long id) throws RecordNotFoundException {
        Optional<Record> recordOptional = recordRepository.findById(id);
        if (recordOptional.isPresent()) {
            recordRepository.deleteById(id);
            return recordOptional.get();
        }
        throw new RecordNotFoundException("Delete null record");
    }

    public Record addRecord(Record record) throws BookLimitException {
        if (bookService.getCountOfBook(record.getBook().getId()) > 0
                && getCountOfBooksAtClient(record.getClient().getId()) < 10) {
            BookType bookType = record.getBook().getBookType();
            record.getBook().decrementOn(1);
            bookService.updateBook(record.getBook());
            bookTypeService.updateBooKType(bookType);
            return recordRepository.saveAndFlush(record);
        }
        throw new BookLimitException("limit books is 10");
    }

    public Record updateRecord(Record record) throws RecordNotFoundException {
        Optional<Record> recordOptional = recordRepository.findById(record.getId());
        if (recordOptional.isPresent()) {
            recordRepository.saveAndFlush(record);
            return record;
        }
        throw new RecordNotFoundException("Update null record");
    }

    public Record updateRecordReturnBook(long id) throws RecordNotFoundException {
        Optional<Record> recordOptional = recordRepository.findById(id);
        if (recordOptional.isPresent()) {
            Record record = recordOptional.get();
            if (record.getDateReturn() == null) {
                record.setDateReturn(Timestamp.valueOf(LocalDateTime.now()));
                recordRepository.saveAndFlush(record);
                record.getBook().incrementOn(1);
                bookTypeService.updateBooKType(record.getBook().getBookType());
                return record;
            }
            return record;
        }
        throw new RecordNotFoundException("Update null record");
    }

    public int getCountOfBooksAtClient(long id) {
        return recordRepository.countByDateReturnIsNullAndClientEquals(id);
    }
}
