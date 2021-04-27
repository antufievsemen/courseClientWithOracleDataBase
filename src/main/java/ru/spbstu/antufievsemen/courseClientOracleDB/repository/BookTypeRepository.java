package ru.spbstu.antufievsemen.courseClientOracleDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;

public interface BookTypeRepository extends JpaRepository<BookType, Long> {
    @Query(value = "Select bt.count from book_types bt  WHERE ID = ?1",
    nativeQuery = true)
    int countOfBookTypeEquals(long id);
}
