package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;

@Service
public class ManagerService {

    private final BookService bookService;
    private final BookTypeService bookTypeService;
    private final ClientService clientService;
    private final RecordService recordService;

    public ManagerService(BookService bookService, BookTypeService bookTypeService, ClientService clientService, RecordService recordService) {
        this.bookService = bookService;
        this.bookTypeService = bookTypeService;
        this.clientService = clientService;
        this.recordService = recordService;
    }

    public Integer countOfBookByClient(long id) {
        return recordService.getCountOfBooksAtClient(id);
    }

    public int getLargestFine() {
        return recordService.getLargestFine();
    }

    public int getFineByClientId(long id) {
        List<Record> records = recordService.getRecordsByClientId(id);
        int result = 0;
        for (Record record : records) {
            Timestamp dateReturn = record.getDateReturn();
            Timestamp dateEnd = record.getDateEnd();
            if (dateReturn.after(dateEnd)) {
                long time = dateReturn.getTime() - dateEnd.getTime();
                long days = TimeUnit.DAYS.toDays(time);
                result += days * record.getBook().getBookType().getFine();
            }
        }
        return result;
    }

    public List<Book> getThreePopularBooks() {
        return recordService.getThreePopularBooks();
    }
}
