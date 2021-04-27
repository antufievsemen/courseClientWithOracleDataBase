package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookLimitException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.DeleteNullRecordException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.UpdateNullBookTypeException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.UpdateNullRecordException;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.RecordRepository;

@Service
public class RecordService{

    private final RecordRepository recordRepository;
    private final BookTypeService bookTypeService;

    public RecordService(RecordRepository recordRepository, BookTypeService bookTypeService) {
        this.recordRepository = recordRepository;
        this.bookTypeService = bookTypeService;
    }

    public List<Record> getRecords() {
        return recordRepository.findAll();
    }

    public Record getRecordById(long id) {
        return recordRepository.getOne(id);
    }

    public Record deleteRecord(long id) throws DeleteNullRecordException {
        Optional<Record> recordOptional = recordRepository.findById(id);
        if (recordOptional.isPresent()) {
            recordRepository.deleteById(id);
            return recordOptional.get();
        }
        throw new DeleteNullRecordException("Delete null record");
    }

    public Record addRecord(Record record) throws BookLimitException {
        if (getCountOfBooksAtClient(record.getId()) >= 10) {
            throw new BookLimitException("limit books is 10");
        }
        record.getBook().getBookType().decrementCount();
        return recordRepository.saveAndFlush(record);
    }

    public Record updateRecord(Record record) throws UpdateNullRecordException {
        Optional<Record> recordOptional = recordRepository.findById(record.getId());
        if (recordOptional.isPresent()) {
            recordRepository.saveAndFlush(record);
            return record;
        }
        throw new UpdateNullRecordException("Update null record");
    }

    public Record updateRecordReturnBook(Record record) throws UpdateNullRecordException, UpdateNullBookTypeException {
        Optional<Record> recordOptional = recordRepository.findById(record.getId());
        if (recordOptional.isPresent()) {
            record.setDateReturn(Timestamp.valueOf(LocalDateTime.now()));
            recordRepository.saveAndFlush(record);
            record.getBook().getBookType().incrementCount();
            bookTypeService.updateBooKType(record.getBook().getBookType());
            return record;
        }
        throw new UpdateNullRecordException("Update null record");
    }

    public int getCountOfBooksAtClient(long id) {
        return recordRepository.countByDateReturnIsNullAndClientEquals(id);
    }
}
