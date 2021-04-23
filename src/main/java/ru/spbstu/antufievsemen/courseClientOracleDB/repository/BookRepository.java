package ru.spbstu.antufievsemen.courseClientOracleDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
