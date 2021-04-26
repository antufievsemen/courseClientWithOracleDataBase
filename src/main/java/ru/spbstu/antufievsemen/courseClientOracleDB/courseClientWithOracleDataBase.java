package ru.spbstu.antufievsemen.courseClientOracleDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.BookTypeService;

@SpringBootApplication
public class courseClientWithOracleDataBase {

    public static void main(String[] args) {
        SpringApplication.run(courseClientWithOracleDataBase.class, args);
    }

    @Bean
    public boolean scriptCreateBookTypes(BookTypeService bookTypeService) {
        BookType bookType = new BookType("common", 0, 10,60);
        BookType bookType1 = new BookType("rare", 0, 50,21);
        BookType bookType2 = new BookType("unique", 0, 300,7);
        bookTypeService.addBookType(bookType);
        bookTypeService.addBookType(bookType1);
        bookTypeService.addBookType(bookType2);
        return true;
    }
}
