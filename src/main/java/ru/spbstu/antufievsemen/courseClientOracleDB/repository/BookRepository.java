package ru.spbstu.antufievsemen.courseClientOracleDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsBookByNameAndBookType(String name, BookType bookType);
}
