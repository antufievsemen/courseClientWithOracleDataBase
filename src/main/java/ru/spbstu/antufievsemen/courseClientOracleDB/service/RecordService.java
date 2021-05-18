package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Client;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookLimitException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.ClientNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.RecordNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.RecordRepository;

@Service
@Transactional(rollbackOn = {RecordNotFoundException.class, BookLimitException.class})
public class RecordService {

    private final RecordRepository recordRepository;
    private final BookTypeService bookTypeService;
    private final BookService bookService;
    private final ClientService clientService;

    public RecordService(RecordRepository recordRepository, BookTypeService bookTypeService, BookService bookService, ClientService clientService) {
        this.recordRepository = recordRepository;
        this.bookTypeService = bookTypeService;
        this.bookService = bookService;
        this.clientService = clientService;
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
            Optional<Book> bookOptional = bookService.getBookById(record.getBook().getId());
            Optional<Client> clientOptional = clientService.getClientById(record.getClient().getId());
            if (bookOptional.isEmpty()) {
                throw new BookNotFoundException("book not exist");
            } else if (clientOptional.isEmpty()) {
                throw new ClientNotFoundException("client not exist");
            }
            bookOptional.get().decrementOn(1);
            bookOptional.get().getBookType().decrementOn(1);
            record.setBook(bookOptional.get());
            record.setClient(clientOptional.get());
            return recordRepository.saveAndFlush(record);
        }
        throw new BookLimitException("limit books is 10");
    }

    public Record updateRecordReturnBook(long id) throws RecordNotFoundException {
        Optional<Record> recordOptional = recordRepository.findById(id);
        if (recordOptional.isPresent()) {
            Record record = recordOptional.get();
            if (record.getDateReturn() == null) {
                record.setDateReturn(Timestamp.valueOf(LocalDateTime.now()));
                record.getBook().incrementOn(1);
                record.getBook().getBookType().incrementOn(1);
                return recordRepository.saveAndFlush(record);
            }
            return record;
        }
        throw new RecordNotFoundException("Update null record");
    }

    public Integer getCountOfBooksAtClient(long id) {
        return recordRepository.countAllByDateReturnIsNullAndClientId(id);
    }

    public List<Record> getRecordsByDateReturnIsNotNullAndClient(long id) {
        return recordRepository.getRecordsByDateReturnIsNotNullAndClientId(id);
    }

    public Integer getLargesFine() {
        return recordRepository.getLargestFine();
    }

    public List<Map<String, BigDecimal>> getThreePopularBooks() {
        return recordRepository.getThreePopularBooks();
    }
}
