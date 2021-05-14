package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;

@Service
public class ManagerService {

    private final RecordService recordService;
    private final BookService bookService;

    public ManagerService(RecordService recordService, BookService bookService) {
        this.recordService = recordService;
        this.bookService = bookService;
    }

    public Integer countOfBookByClient(long id) {
        return recordService.getCountOfBooksAtClient(id);
    }

    public int getLargestFine() {
        return recordService.getLargesFine();
    }

    public int getFineByClientId(long id) {
        List<Record> records = recordService.getRecordsByDateReturnIsNotNullAndClient(id);
        int result = 0;
        for (Record record : records) {
            Timestamp dateReturn = record.getDateReturn();
            Timestamp dateEnd = record.getDateEnd();
            if (dateReturn.after(dateEnd)) {
                long time = dateReturn.getTime() - dateEnd.getTime();
                long days = TimeUnit.MILLISECONDS.toDays(time);
                result += days * record.getBook().getBookType().getFine();
            }
        }
        return result;
    }

    public List<Book> getThreePopularBooks() {
        List<Book> resultList = new ArrayList<>();
        List<Map<String, BigDecimal>> map = recordService.getThreePopularBooks();
        for (Map<String, BigDecimal> stringBigDecimalMap : map) {
            Optional<Book> book = bookService.getBookById(stringBigDecimalMap.get("ID").intValueExact());
            book.ifPresent(resultList::add);
        }
        return resultList;
    }
}
