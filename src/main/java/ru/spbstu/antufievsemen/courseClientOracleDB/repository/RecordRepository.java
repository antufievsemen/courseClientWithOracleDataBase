package ru.spbstu.antufievsemen.courseClientOracleDB.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {

    int countAllByDateReturnIsNullAndClientId(long id);

    @Query(nativeQuery = true,
    value = "Select MAX(SUM(bt.FINE * EXTRACT( day from j.DATE_RETURN - j.DATE_END))) as result From JOURNAL j\n" +
            "JOIN BOOKS b On j.BOOK_ID = b.ID\n" +
            "JOIN BOOK_TYPES bt On bt.ID = b.BOOK_TYPE_ID\n" +
            "Where (DATE_END < DATE_RETURN)\n" +
            "Group by j.CLIENT_ID")
    Integer getLargestFine();

    @Query(nativeQuery = true,
            value = "Select * From (Select b.ID, COUNT(b.ID) From journal j\n" +
                    "JOIN books b ON j.book_id = b.id\n" +
                    "GROUP BY b.id\n" +
                    "Order by COUNT(b.ID) DESC)\n" +
                    "    Where ROWNUM <= 3")
    List<Map<String, BigDecimal>> getThreePopularBooks();

    List<Record> getRecordsByDateReturnIsNotNullAndClientId(long id);
}
