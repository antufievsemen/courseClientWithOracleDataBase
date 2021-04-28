package ru.spbstu.antufievsemen.courseClientOracleDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "Select COUNT from BOOKS  WHERE ID = ?1",
            nativeQuery = true)
    int countOfBook(long id);
}
