package ru.spbstu.antufievsemen.courseClientOracleDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookLimitException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.DeleteNullBookException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.UpdateNullBookException;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.UpdateNullBookTypeException;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.BookService;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.BookTypeService;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.ClientService;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.RecordService;

@SpringBootApplication
public class courseClientWithOracleDataBase {

    public static void main(String[] args) {
        SpringApplication.run(courseClientWithOracleDataBase.class, args);
    }

    @Bean
    public boolean scriptCreateBookTypes(BookTypeService bookTypeService,
                                         ClientService clientService,
                                         BookService bookService,
                                         RecordService recordService) throws BookLimitException, UpdateNullBookTypeException, UpdateNullBookException, DeleteNullBookException {
        BookType bookType = new BookType("common", 0, 10, 60);
        BookType bookType1 = new BookType("rare", 0, 50, 21);
        BookType bookType2 = new BookType("unique", 0, 300, 7);
        bookTypeService.addBookType(bookType);
        bookTypeService.addBookType(bookType1);
        bookTypeService.addBookType(bookType2);
//        Book book = new Book("book", 11, bookType);
//        bookService.addBook(book);
//        Client client = new Client("123", "123", "123", "123", "123");
//        clientService.addClient(client);
//        for (int i = 0; i < 10; i++) {
//            recordService.addRecord(new Record(book, client));
//        }
        return true;
    }
}
