package ru.spbstu.antufievsemen.courseClientOracleDB;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Client;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.BookService;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.BookTypeService;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.ClientService;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.RecordService;

@SpringBootApplication
@EnableTransactionManagement
public class courseClientWithOracleDataBase {

    public static void main(String[] args) {
        SpringApplication.run(courseClientWithOracleDataBase.class, args);
    }

    @Bean
    public boolean scriptCreateBookTypes(BookTypeService bookTypeService,
                                         ClientService clientService,
                                         BookService bookService,
                                         RecordService recordService) {
        BookType bookType = new BookType("common", 0, 10, 60);
        BookType bookType1 = new BookType("rare", 0, 50, 21);
        BookType bookType2 = new BookType("unique", 0, 300, 7);
        bookTypeService.addBookType(bookType);
        bookTypeService.addBookType(bookType1);
        bookTypeService.addBookType(bookType2);
        Book book = new Book("book", 11, bookType);
        bookService.addBook(book);
        Client client = new Client("123", "123", "123", "123", "123");
        clientService.addClient(client);
//        Record record = new Record(book, client, Timestamp.valueOf(LocalDateTime.now().plusDays(200)));
          Record record = new Record(book, client);
        recordService.addRecord(record);
        return true;
    }
}
