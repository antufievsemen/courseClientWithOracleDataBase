package ru.spbstu.antufievsemen.courseClientOracleDB.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(nativeQuery = true,
            value = "Select COUNT(*) From journal \n" +
            "    Where client_id = ?1 AND (date_return) IS NULL")
    int countByDateReturnIsNullAndClientEquals(long id);

    @Query(value = "Select MAX(SUM(bt.FINE * EXTRACT( day from j.DATE_RETURN - j.DATE_END))) as result From JOURNAL j\n" +
            "JOIN BOOKS b On j.BOOK_ID = b.ID\n" +
            "JOIN BOOK_TYPES bt On bt.ID = b.BOOK_TYPE_ID\n" +
            "Where (DATE_END <= DATE_RETURN)\n" +
            "Group by j.CLIENT_ID;", nativeQuery = true)
    int getLargestFine();

    List<Record> getRecordsByClientId(long id);

    @Query(nativeQuery = true,
            value = "WITH\n" +
            "popular_books as (Select b.NAME, COUNT(b.NAME) From JOURNAL j\n" +
            "JOIN BOOKS b ON j.BOOK_ID = b.ID\n" +
            "GROUP BY NAME\n" +
            "Order by COUNT(b.NAME) DESC)\n" +
            "Select * From popular_books\n" +
            "    Where ROWNUM <= 3;")
    List<Book> getThreePopularBooks();
}
