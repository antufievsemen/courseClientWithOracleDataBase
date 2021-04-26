package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.RecordRepository;

import java.util.List;

@Service
public class RecordService{

    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<Record> getRecords() {
        return recordRepository.findAll();
    }

    public Record getRecordById(long id) {
        return recordRepository.getOne(id);
    }

    public boolean deleteRecord(long id) {
        if (recordRepository.existsById(id)) {
            recordRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean addRecord(Record record) {
        if (record == null
                || recordRepository.existsById(record.getId())) {
            return false;
        }
        recordRepository.saveAndFlush(record);
        return true;
    }

    public boolean updateRecord(Record record) {
        if (recordRepository.existsById(record.getId())) {
            recordRepository.saveAndFlush(record);
            return true;
        }
        return false;
    }
}
