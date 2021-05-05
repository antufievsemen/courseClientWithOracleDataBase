package ru.spbstu.antufievsemen.courseClientOracleDB.web.controller.rest;

import java.util.List;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.RecordNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.RecordService;

@RestController
@RequestMapping("/library/journal")
@EnableTransactionManagement
public class RestControllerRecord {

    private final RecordService recordService;

    public RestControllerRecord(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    public List<Record> getList() {
        return recordService.getRecords();
    }

    @GetMapping("/{id}")
    public Record getOne(@PathVariable long id) {
        return recordService.getRecordById(id).
                orElseGet(()-> {throw new RecordNotFoundException("record not found");});
    }

    @PostMapping("/addRecord")
    public Record addRecord(@RequestBody Record record) {
        return recordService.addRecord(record);
    }

    @PutMapping("/updateRecord/{id}")
    public Record updateRecord(@RequestBody Record record, @PathVariable long id) {
        record.setId(id);
        return recordService.updateRecord(record);
    }

    @PutMapping("/updateRecordReturnDate/{id}")
    public Record finishRecord(@PathVariable long id) {
        return recordService.updateRecordReturnBook(id);
    }

    @DeleteMapping("/deleteRecord/{id}")
    public Record deleteRecord(@PathVariable long id) {
        return recordService.deleteRecord(id);
    }
}
